package org.soccer.smartbet.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;
    
    private String marketType;
    private String predictedOutcome;
    private Double confidenceScore;
    private Double predictedOdds;
    
    private LocalDateTime predictionDate;
    private boolean isCorrect;
    
    // Constructors
    public Prediction() {
        this.predictionDate = LocalDateTime.now();
    }
    
    public Prediction(Fixture fixture, String marketType, String predictedOutcome) {
        this();
        this.fixture = fixture;
        this.marketType = marketType;
        this.predictedOutcome = predictedOutcome;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Fixture getFixture() { return fixture; }
    public void setFixture(Fixture fixture) { this.fixture = fixture; }
    public String getMarketType() { return marketType; }
    public void setMarketType(String marketType) { this.marketType = marketType; }
    public String getPredictedOutcome() { return predictedOutcome; }
    public void setPredictedOutcome(String predictedOutcome) { this.predictedOutcome = predictedOutcome; }
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
    public Double getPredictedOdds() { return predictedOdds; }
    public void setPredictedOdds(Double predictedOdds) { this.predictedOdds = predictedOdds; }
    public LocalDateTime getPredictionDate() { return predictionDate; }
    public void setPredictionDate(LocalDateTime predictionDate) { this.predictionDate = predictionDate; }
    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}