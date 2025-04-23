package org.soccer.smartbet.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Team {
    @Id
    private Integer id;
    private String name;
    private String logo;
    private String country;
    
    // Statistics fields
    private Double attackRating;
    private Double defenseRating;
    private Double homePerformance;
    private Double awayPerformance;
    
    @OneToMany(mappedBy = "homeTeam")
    private List<Fixture> homeFixtures;
    
    @OneToMany(mappedBy = "awayTeam")
    private List<Fixture> awayFixtures;
    
    // Constructors
    public Team() {}
    
    public Team(Integer id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Double getAttackRating() { return attackRating; }
    public void setAttackRating(Double attackRating) { this.attackRating = attackRating; }
    public Double getDefenseRating() { return defenseRating; }
    public void setDefenseRating(Double defenseRating) { this.defenseRating = defenseRating; }
    public Double getHomePerformance() { return homePerformance; }
    public void setHomePerformance(Double homePerformance) { this.homePerformance = homePerformance; }
    public Double getAwayPerformance() { return awayPerformance; }
    public void setAwayPerformance(Double awayPerformance) { this.awayPerformance = awayPerformance; }
    public List<Fixture> getHomeFixtures() { return homeFixtures; }
    public void setHomeFixtures(List<Fixture> homeFixtures) { this.homeFixtures = homeFixtures; }
    public List<Fixture> getAwayFixtures() { return awayFixtures; }
    public void setAwayFixtures(List<Fixture> awayFixtures) { this.awayFixtures = awayFixtures; }
    
    // Helper method
    public String getFullName() {
        return name + " (" + country + ")";
    }
}