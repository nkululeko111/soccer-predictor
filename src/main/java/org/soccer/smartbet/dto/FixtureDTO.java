package org.soccer.smartbet.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class FixtureDTO {
    private Integer id;
    private String leagueName;
    private String homeTeamName;
    private String awayTeamName;
    private LocalDateTime date;
    private String status;
    private Integer goalsHome;
    private Integer goalsAway;
    private Integer goalsHomeHalfTime;
    private Integer goalsAwayHalfTime;
    private Map<String, Double> odds;
    
    public FixtureDTO(Integer id, String leagueName, String homeTeamName, 
                    String awayTeamName, LocalDateTime date) {
        this.id = id;
        this.leagueName = leagueName;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.date = date;
    }
    
    // Helper method to check if fixture is upcoming
    public boolean isUpcoming() {
        return date.isAfter(LocalDateTime.now()) && !"FT".equals(status);
    }
}