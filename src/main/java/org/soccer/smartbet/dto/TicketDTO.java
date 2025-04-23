package org.soccer.smartbet.dto;

import lombok.Data;
import org.soccer.smartbet.domain.BettingTicket.TicketType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketDTO {
    private Long id;
    private String name;
    private TicketType type;
    private Double totalOdds;
    private Double stake;
    private Double potentialWin;
    private LocalDateTime createdDate;
    private boolean isWon;
    private List<SelectionDTO> selections;
    
    @Data
    public static class SelectionDTO {
        private Long id;
        private Integer fixtureId;
        private String homeTeam;
        private String awayTeam;
        private String marketType;
        private String prediction;
        private Double odds;
        private Double confidence;
    }
    
    // Helper method to check if ticket is active
    public boolean isActive() {
        return selections.stream()
            .allMatch(selection -> selection.getFixture().isUpcoming());
    }
}