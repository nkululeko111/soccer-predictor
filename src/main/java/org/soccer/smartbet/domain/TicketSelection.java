package org.soccer.smartbet.domain;

import javax.persistence.*;

@Entity
public class TicketSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private BettingTicket ticket;
    
    private String marketType;
    private String prediction;
    private Double odds;
    private Double confidence;
    private boolean isCorrect;
    
    // Constructors
    public TicketSelection() {}
    
    public TicketSelection(Fixture fixture, String marketType, String prediction, Double odds) {
        this.fixture = fixture;
        this.marketType = marketType;
        this.prediction = prediction;
        this.odds = odds;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Fixture getFixture() { return fixture; }
    public void setFixture(Fixture fixture) { this.fixture = fixture; }
    public BettingTicket getTicket() { return ticket; }
    public void setTicket(BettingTicket ticket) { this.ticket = ticket; }
    public String getMarketType() { return marketType; }
    public void setMarketType(String marketType) { this.marketType = marketType; }
    public String getPrediction() { return prediction; }
    public void setPrediction(String prediction) { this.prediction = prediction; }
    public Double getOdds() { return odds; }
    public void setOdds(Double odds) { this.odds = odds; }
    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}