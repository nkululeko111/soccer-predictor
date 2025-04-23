package org.soccer.smartbet.service;

import org.soccer.smartbet.model.Ticket;
import org.soccer.smartbet.model.TicketItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final Random random = new Random();

    public List<Ticket> generateTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(generateFirstHalfFocusedTicket());
        tickets.add(generateGeneralTicket("General Market A"));
        tickets.add(generateGeneralTicket("General Market B"));
        return tickets;
    }

    private Ticket generateFirstHalfFocusedTicket() {
        List<TicketItem> items = new ArrayList<>();
        double totalOdds = 1.0;

        for (int i = 0; i < 12; i++) {
            TicketItem item = new TicketItem();
            item.setHomeTeam("TeamA" + i);
            item.setAwayTeam("TeamB" + i);
            item.setMarket("1st Half Over 0.5");
            item.setPrediction("Over");
            item.setOdd(randomOdd(1.3, 1.8));
            items.add(item);
            totalOdds *= item.getOdd();
        }

        Ticket ticket = new Ticket();
        ticket.setName("First Half Special");
        ticket.setMatches(items);
        ticket.setTotalOdds(round(totalOdds));
        return ticket;
    }

    private Ticket generateGeneralTicket(String name) {
        List<String> markets = Arrays.asList(
                "Match Result (1X2)", "Both Teams to Score", "Over 2.5 Goals",
                "Asian Handicap", "Draw No Bet", "Double Chance",
                "Winning Margin", "1st Half Winner", "Correct Score"
        );

        List<TicketItem> items = new ArrayList<>();
        double totalOdds = 1.0;

        for (int i = 0; i < 12; i++) {
            TicketItem item = new TicketItem();
            item.setHomeTeam("TeamA" + i);
            item.setAwayTeam("TeamB" + i);
            item.setMarket(markets.get(random.nextInt(markets.size())));
            item.setPrediction(generateRandomPrediction(item.getMarket()));
            item.setOdd(randomOdd(1.3, 2.0));
            items.add(item);
            totalOdds *= item.getOdd();
        }

        Ticket ticket = new Ticket();
        ticket.setName(name);
        ticket.setMatches(items);
        ticket.setTotalOdds(round(totalOdds));
        return ticket;
    }

    private double randomOdd(double min, double max) {
        return round(min + (max - min) * random.nextDouble());
    }

    private String generateRandomPrediction(String market) {
        return switch (market) {
            case "Match Result (1X2)" -> List.of("1", "X", "2").get(random.nextInt(3));
            case "Both Teams to Score" -> random.nextBoolean() ? "Yes" : "No";
            case "Over 2.5 Goals" -> "Over";
            case "Asian Handicap" -> "+1.5 Home";
            case "Draw No Bet" -> "Home";
            case "Double Chance" -> "1X";
            case "Winning Margin" -> "Home by 1";
            case "1st Half Winner" -> random.nextBoolean() ? "Home" : "Away";
            case "Correct Score" -> "2-1";
            default -> "Unknown";
        };
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}
