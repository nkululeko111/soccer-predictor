package org.soccer.smartbet.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BettingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Double totalOdds;
    private Double stake;
    private Double potentialWin;
    
    @Enumerated(EnumType.STRING)
    private TicketType type;
    
    private LocalDateTime createdDate;
    private boolean isWon;
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketSelection> selections = new ArrayList<>();
    
    // Constructors
    public BettingTicket() {
        this.createdDate = LocalDateTime.now();
    }
    
    public BettingTicket(String name, TicketType type) {
        this();
        this.name = name;
        this.type = type;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getTotalOdds() { return totalOdds; }
    public void setTotalOdds(Double totalOdds) { 
        this.totalOdds = totalOdds;
        calculatePotentialWin();
    }
    public Double getStake() { return stake; }
    public void setStake(Double stake) { 
        this.stake = stake;
        calculatePotentialWin();
    }
    public Double getPotentialWin() { return potentialWin; }
    public TicketType getType() { return type; }
    public void setType(TicketType type) { this.type = type; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public boolean isWon() { return isWon; }
    public void setWon(boolean won) { isWon = won; }
    public List<TicketSelection> getSelections() { return selections; }
    public void setSelections(List<TicketSelection> selections) { this.selections = selections; }
    
    // Helper methods
    private void calculatePotentialWin() {
        if (totalOdds != null && stake != null) {
            this.potentialWin = totalOdds * stake;
        }
    }
    
    public void addSelection(TicketSelection selection) {
        selections.add(selection);
        selection.setTicket(this);
        updateTotalOdds();
    }
    
    public void removeSelection(TicketSelection selection) {
        selections.remove(selection);
        selection.setTicket(null);
        updateTotalOdds();
    }
    
    private void updateTotalOdds() {
        this.totalOdds = selections.stream()
            .map(TicketSelection::getOdds)
            .reduce(1.0, (a, b) -> a * b);
        calculatePotentialWin();
    }
    
    public enum TicketType {
        FIRST_HALF, HIGH_ODDS, SAFE_BETS, MIXED
    }
}