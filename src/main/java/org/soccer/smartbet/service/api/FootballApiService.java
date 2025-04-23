package org.soccer.smartbet.service.api;

import org.soccer.smartbet.domain.Fixture;
import org.soccer.smartbet.domain.League;
import org.soccer.smartbet.domain.Team;
import org.soccer.smartbet.dto.FixtureDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class FootballApiService {

    private final ApiClient apiClient;
    private final ApiResponseParser parser;

    public FootballApiService(ApiClient apiClient, ApiResponseParser parser) {
        this.apiClient = apiClient;
        this.parser = parser;
    }

    @Cacheable("leagues")
    public List<League> getTopLeagues(int count) {
        String response = apiClient.get("/leagues?current=true");
        List<League> leagues = parser.parseLeagues(response);
        return leagues.stream()
            .sorted((l1, l2) -> Integer.compare(l2.getPopularity(), l1.getPopularity()))
            .limit(count)
            .toList();
    }

    @Cacheable(value = "fixtures", key = "{#leagueId, #date}")
    public Page<FixtureDTO> getFixtures(Integer leagueId, LocalDate date, Pageable pageable) {
        String endpoint = "/fixtures";
        if (leagueId != null) endpoint += "?league=" + leagueId;
        if (date != null) endpoint += (leagueId != null ? "&" : "?") + "date=" + date;
        
        String response = apiClient.get(endpoint);
        List<Fixture> fixtures = parser.parseFixtures(response);
        return parser.toPage(fixtures, pageable).map(this::convertToDTO);
    }

    @Cacheable("fixtureDetails")
    public FixtureDTO getFixtureDetails(int id) {
        String response = apiClient.get("/fixtures?id=" + id);
        Fixture fixture = parser.parseFixtureDetails(response);
        return convertToDTO(fixture);
    }

    @Cacheable("teamStats")
    public Map<String, Object> getTeamStats(int teamId, int leagueId, int season) {
        String response = apiClient.get(
            String.format("/teams/statistics?team=%d&league=%d&season=%d", 
            teamId, leagueId, season));
        return parser.parseTeamStats(response);
    }

    private FixtureDTO convertToDTO(Fixture fixture) {
        return new FixtureDTO(
            fixture.getId(),
            fixture.getLeague().getName(),
            fixture.getHomeTeam().getName(),
            fixture.getAwayTeam().getName(),
            fixture.getDate()
        );
    }
}