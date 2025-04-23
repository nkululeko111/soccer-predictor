package org.soccer.smartbet.service.ticket;

import org.soccer.smartbet.domain.BettingTicket;
import org.soccer.smartbet.domain.TicketSelection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OddsCalculator {

    public double calculateTotalOdds(List<TicketSelection> selections) {
        return selections.stream()
            .mapToDouble(TicketSelection::getOdds)
            .reduce(1.0, (a, b) -> a * b);
    }

    public double calculatePotentialWin(BettingTicket ticket) {
        return ticket.getStake() != null ? 
            ticket.getStake() * ticket.getTotalOdds() : 0.0;
    }

    public double calculateKellyCriterionStake(double probability, double odds) {
        // Kelly Criterion formula implementation
        return (probability * odds - (1 - probability)) / odds;
    }
}