package org.soccer.smartbet.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
public class Fixture {
    @Id
    private Integer id;  // Using API's fixture ID
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id")
    private League league;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;
    
    private LocalDateTime date;
    private String status;
    private Integer goalsHome;
    private Integer goalsAway;
    private Integer goalsHomeHalfTime;
    private Integer goalsAwayHalfTime;
    
    @ElementCollection
    @CollectionTable(name = "fixture_odds", joinColumns = @JoinColumn(name = "fixture_id"))
    @MapKeyColumn(name = "market_type")
    @Column(name = "odds_value")
    private Map<String, Double> odds;  // Stores odds for different markets
    
    // Constructors
    public Fixture() {}
    
    public Fixture(Integer id, League league, Team homeTeam, Team awayTeam, LocalDateTime date) {
        this.id = id;
        this.league = league;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public League getLeague() { return league; }
    public void setLeague(League league) { this.league = league; }
    public Team getHomeTeam() { return homeTeam; }
    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getGoalsHome() { return goalsHome; }
    public void setGoalsHome(Integer goalsHome) { this.goalsHome = goalsHome; }
    public Integer getGoalsAway() { return goalsAway; }
    public void setGoalsAway(Integer goalsAway) { this.goalsAway = goalsAway; }
    public Integer getGoalsHomeHalfTime() { return goalsHomeHalfTime; }
    public void setGoalsHomeHalfTime(Integer goalsHomeHalfTime) { this.goalsHomeHalfTime = goalsHomeHalfTime; }
    public Integer getGoalsAwayHalfTime() { return goalsAwayHalfTime; }
    public void setGoalsAwayHalfTime(Integer goalsAwayHalfTime) { this.goalsAwayHalfTime = goalsAwayHalfTime; }
    public Map<String, Double> getOdds() { return odds; }
    public void setOdds(Map<String, Double> odds) { this.odds = odds; }
    
    // Helper methods
    public boolean isCompleted() {
        return "FT".equals(status) || "AET".equals(status) || "PEN".equals(status);
    }
}