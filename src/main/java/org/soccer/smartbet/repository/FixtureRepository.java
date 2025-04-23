package org.soccer.smartbet.repository;

import org.soccer.smartbet.domain.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Integer> {

    // Find fixtures by league
    List<Fixture> findByLeagueId(Integer leagueId);

    // Find upcoming fixtures
    List<Fixture> findByDateAfter(LocalDateTime date);

    // Find fixtures between dates
    List<Fixture> findByDateBetween(LocalDateTime start, LocalDateTime end);

    // Find fixtures with goals in first half
    @Query("SELECT f FROM Fixture f WHERE f.goalsHomeHalfTime + f.goalsAwayHalfTime > ?1")
    List<Fixture> findWithFirstHalfGoals(int minGoals);

    // Find by team (either home or away)
    @Query("SELECT f FROM Fixture f WHERE f.homeTeam.id = ?1 OR f.awayTeam.id = ?1")
    List<Fixture> findByTeamId(Integer teamId);
}