package org.soccer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        SoccerDataFetcher apiSportsFetcher = new SoccerDataFetcher();
        FootballDataFetcher footballDataFetcher = new FootballDataFetcher();
        BettingAI bettingAI = new BettingAI();

        // Get the current date and calculate the date range
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2024, 9, 23);  // Hardcoding the end date for this example

        // Format dates as strings
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String fromDate = currentDate.format(formatter);
        String toDate = endDate.format(formatter);

        String[] leagueIds = {"39", "140"};  // Example league IDs (Premier League, La Liga, Serie A, Bundesliga)
        String season = "2024";

        // Fetch fixtures for the week
        try {
            System.out.println("Fetching weekly fixtures from API-SPORTS for all leagues...");
            apiSportsFetcher.fetchFixturesForAllLeagues(leagueIds, season, fromDate, toDate);

            // Example usage of AI predictions (ensure this logic makes sense in your context)
            bettingAI.predictBettingCode("Team A", "Team B", "2024-09-21");
        } catch (Exception e) {
            System.out.println("API-SPORTS failed. Switching to Football-Data.org...");
            footballDataFetcher.fetchFixtures();  // Fetches for a default set or all available leagues

            // Example usage of AI predictions
            bettingAI.predictBettingCode("Team C", "Team D", "2024-09-22");
        }
    }
}
