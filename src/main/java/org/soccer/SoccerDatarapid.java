package org.soccer;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

    public class SoccerDatarapid {

        // API keys
        private static final String SPORT_API_KEY = "a4ed075e21msh9d262cb6a54bc9bp164b07jsn29409cbfbe11";
        private static final String API_FOOTBALL_KEY = "a4ed075e21msh9d262cb6a54bc9bp164b07jsn29409cbfbe11";

        // Base URLs
        private static final String SPORT_API_URL = "https://sportapi7.p.rapidapi.com/api/v1/event/xdbsZdb/h2h/events";
        private static final String API_FOOTBALL_URL = "https://api-football-v1.p.rapidapi.com/v2/odds/league/865927/bookmaker/5?page=2";

        // Method to fetch head-to-head events from Sport API
        public static void fetchHeadToHeadEvents() {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SPORT_API_URL))
                        .header("x-rapidapi-host", "sportapi7.p.rapidapi.com")
                        .header("x-rapidapi-key", SPORT_API_KEY)
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Parse and display the JSON response
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                System.out.println("Head-to-Head Events from Sport API:");
                System.out.println(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Method to fetch odds from API-FOOTBALL
        public static void fetchOddsFromApiFootball() {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_FOOTBALL_URL))
                        .header("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                        .header("x-rapidapi-key", API_FOOTBALL_KEY)
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Parse and display the JSON response
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                System.out.println("Odds from API-FOOTBALL:");
                System.out.println(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Method to fetch fixtures for the upcoming week from Sport API
        public static void fetchWeeklyFixturesFromSportApi() {
            try {
                HttpClient client = HttpClient.newHttpClient();
                // Calculate the date range for the next 7 days
                LocalDate startDate = LocalDate.now();
                LocalDate endDate = startDate.plusDays(7);
                String startDateStr = startDate.toString();
                String endDateStr = endDate.toString();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SPORT_API_URL + "/fixtures?from=" + startDateStr + "&to=" + endDateStr))
                        .header("x-rapidapi-host", "sportapi7.p.rapidapi.com")
                        .header("x-rapidapi-key", SPORT_API_KEY)
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Parse and display the JSON response
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                System.out.println("Weekly Fixtures from Sport API:");
                System.out.println(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public static void main(String[] args) {
            SoccerDataFetcher fetcher = new SoccerDataFetcher();
            fetchHeadToHeadEvents();
            fetchOddsFromApiFootball();
            fetchWeeklyFixturesFromSportApi();

        }
    }


