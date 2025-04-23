package org.soccer.smartbet.service.api;

import com.google.gson.*;
import org.soccer.smartbet.domain.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ApiResponseParser {

    private static final DateTimeFormatter DATE_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    public List<League> parseLeagues(String json) {
        List<League> leagues = new ArrayList<>();
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray response = root.getAsJsonArray("response");
        
        for (JsonElement element : response) {
            JsonObject leagueObj = element.getAsJsonObject().getAsJsonObject("league");
            League league = new League();
            league.setId(leagueObj.get("id").getAsInt());
            league.setName(leagueObj.get("name").getAsString());
            league.setCountry(leagueObj.get("country").getAsString());
            league.setLogo(leagueObj.get("logo").getAsString());
            leagues.add(league);
        }
        return leagues;
    }

    public List<Fixture> parseFixtures(String json) {
        // Similar parsing logic for fixtures
    }

    public Fixture parseFixtureDetails(String json) {
        // Detailed fixture parsing
    }

    public Map<String, Object> parseTeamStats(String json) {
        // Team statistics parsing
    }

    public <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}