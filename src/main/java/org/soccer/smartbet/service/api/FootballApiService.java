package org.soccer.smartbet.service.api;

import org.soccer.smartbet.model.League;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.Comparator;
import java.util.List;

@Service
public class FootballApiService {
    private static final String API_BASE_URL = "https://v3.football.api-sports.io";
    private final String apiKey;
    private final RestTemplate restTemplate;

    public FootballApiService(@Value("${football.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
    }

    public List<League> getTopLeagues(int count) {
        HttpHeaders headers = createHeaders();
        String url = API_BASE_URL + "/leagues?current=true";

        ResponseEntity<LeagueResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), LeagueResponse.class);

        return response.getBody().getResponse().stream()
                .sorted(Comparator.comparingInt(League::getPopularity).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Fixture> getFixtures(int leagueId, String season) {
        // Implementation for fetching fixtures
    }

    public TeamStatistics getTeamStatistics(int teamId, int leagueId, String season) {
        // Implementation for fetching team stats
    }

    public List<BettingOdds> getMatchOdds(int fixtureId) {
        // Implementation for fetching odds
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", apiKey);
        headers.set("x-rapidapi-host", "v3.football.api-sports.io");
        return headers;
    }
}