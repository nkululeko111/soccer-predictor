package org.soccer.smartbet.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class League {
    @Id
    private Integer id;
    private String name;
    private String type;
    private String logo;
    private String country;
    private Integer season;
    private Integer popularity;  // For sorting top leagues
    
    @OneToMany(mappedBy = "league")
    private List<Fixture> fixtures;
    
    // Constructors
    public League() {}
    
    public League(Integer id, String name, String country, Integer season) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.season = season;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Integer getSeason() { return season; }
    public void setSeason(Integer season) { this.season = season; }
    public Integer getPopularity() { return popularity; }
    public void setPopularity(Integer popularity) { this.popularity = popularity; }
    public List<Fixture> getFixtures() { return fixtures; }
    public void setFixtures(List<Fixture> fixtures) { this.fixtures = fixtures; }
}