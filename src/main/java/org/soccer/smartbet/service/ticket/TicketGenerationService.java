package org.soccer.smartbet.service.ticket;

import org.soccer.smartbet.domain.*;
import org.soccer.smartbet.dto.*;
import org.soccer.smartbet.repository.*;
import org.soccer.smartbet.service.ai.PredictionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketGenerationService {

    private final FixtureRepository fixtureRepository;
    private final PredictionService predictionService;
    private final OddsCalculator oddsCalculator;
    private final TicketRepository ticketRepository;

    public TicketGenerationService(FixtureRepository fixtureRepository,
                                 PredictionService predictionService,
                                 OddsCalculator oddsCalculator,
                                 TicketRepository ticketRepository) {
        this.fixtureRepository = fixtureRepository;
        this.predictionService = predictionService;
        this.oddsCalculator = oddsCalculator;
        this.ticketRepository = ticketRepository;
    }

    public BettingTicket generateTicket(String type, String username) {
        List<Fixture> fixtures = getRelevantFixtures(type);
        List<TicketSelection> selections = createSelections(fixtures, type);
        
        BettingTicket ticket = new BettingTicket();
        ticket.setName(generateTicketName(type));
        ticket.setType(BettingTicket.TicketType.valueOf(type.toUpperCase()));
        selections.forEach(ticket::addSelection);
        ticket.setCreatedBy(username);
        
        return ticketRepository.save(ticket);
    }

    private List<Fixture> getRelevantFixtures(String type) {
        switch (type.toLowerCase()) {
            case "first_half":
                return fixtureRepository.findUpcomingFixturesWithHighFirstHalfPotential();
            case "high_odds":
                return fixtureRepository.findFixturesWithHighOdds();
            default:
                return fixtureRepository.findUpcomingFixtures();
        }
    }

    private List<TicketSelection> createSelections(List<Fixture> fixtures, String type) {
        return fixtures.stream()
            .limit(12)
            .map(fixture -> {
                MarketPrediction bestPrediction = predictionService.predictMarkets(fixture)
                    .stream()
                    .filter(p -> isRelevantMarket(p.getMarketType(), type))
                    .max(Comparator.comparingDouble(MarketPrediction::getConfidence))
                    .orElseThrow();
                
                TicketSelection selection = new TicketSelection();
                selection.setFixture(fixture);
                selection.setMarketType(bestPrediction.getMarketType());
                selection.setPrediction(bestPrediction.getPredictedOutcome());
                selection.setOdds(bestPrediction.getOdds());
                selection.setConfidence(bestPrediction.getConfidence());
                return selection;
            })
            .collect(Collectors.toList());
    }

    private boolean isRelevantMarket(String marketType, String ticketType) {
        // Implementation based on ticket type
        return true;
    }

    private String generateTicketName(String type) {
        return String.format("%s Ticket - %s", 
            type.substring(0, 1).toUpperCase() + type.substring(1).replace("_", " "),
            LocalDate.now().toString());
    }
}