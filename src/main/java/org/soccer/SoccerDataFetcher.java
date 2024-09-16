package org.soccer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.http.HttpTimeoutException;

public class SoccerDataFetcher {

    private static final String API_KEY = "13a98ac6c56849e4bbc3519acd6bfa2b";
    private static final String BASE_URL = "https://v3.football.api-sports.io";

    public void fetchFixturesForAllLeagues(String[] leagueIds, String season, String fromDate, String toDate) {
        HttpClient client = HttpClient.newHttpClient();

        for (String leagueId : leagueIds) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/fixtures?league=" + leagueId + "&season=" + season + "&from=" + fromDate + "&to=" + toDate))
                    .header("x-apisports-key", API_KEY)
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Check if the response status code indicates an error
                if (response.statusCode() != 200) {
                    System.out.println("Error: Received HTTP status code " + response.statusCode());
                    System.out.println("Response body: " + response.body());
                    continue; // Skip to the next league
                }

                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonArray fixtures = jsonResponse.getAsJsonArray("response");

                // Print fixture details
                for (int i = 0; i < fixtures.size(); i++) {
                    JsonObject fixtureData = fixtures.get(i).getAsJsonObject();
                    JsonObject teams = fixtureData.getAsJsonObject("teams");
                    String homeTeam = teams.getAsJsonObject("home").get("name").getAsString();
                    String awayTeam = teams.getAsJsonObject("away").get("name").getAsString();
                    String date = fixtureData.getAsJsonObject("fixture").get("date").getAsString();
                    System.out.println("Fixture in League " + leagueId + ": " + homeTeam + " vs " + awayTeam + " on " + date);
                }
            } catch (HttpTimeoutException e) {
                System.out.println("Request timed out: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO Exception: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Request interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore interrupted status
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
