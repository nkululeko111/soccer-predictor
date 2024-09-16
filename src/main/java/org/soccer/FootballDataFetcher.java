package org.soccer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FootballDataFetcher {

    private static final String API_KEY = "e54321c6de8849f99593c24eec0c7669";
    private static final String BASE_URL = "https://api.football-data.org/v4";

    // Method to fetch fixtures for a specific week
    public void fetchFixtures() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Get the current date and calculate the date range
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(7);

            // Format dates as strings
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            String fromDate = startDate.format(formatter);
            String toDate = endDate.format(formatter);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/matches?from=" + fromDate + "&to=" + toDate))
                    .header("X-Auth-Token", API_KEY)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response from Football-Data.org: " + response.body());

            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray matches = jsonResponse.getAsJsonArray("matches");

            // Print fixture details
            for (int i = 0; i < matches.size(); i++) {
                JsonObject match = matches.get(i).getAsJsonObject();
                String homeTeam = match.getAsJsonObject("homeTeam").get("name").getAsString();
                String awayTeam = match.getAsJsonObject("awayTeam").get("name").getAsString();
                String date = match.get("utcDate").getAsString();
                System.out.println("Fixture: " + homeTeam + " vs " + awayTeam + " on " + date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
