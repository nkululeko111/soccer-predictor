package org.soccer.smartbet.repository;

import org.soccer.smartbet.domain.BettingTicket;
import org.soccer.smartbet.domain.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<BettingTicket, Long> {

    List<BettingTicket> findByType(TicketType type);

    @Query("SELECT t FROM BettingTicket t WHERE t.totalOdds BETWEEN ?1 AND ?2")
    List<BettingTicket> findByOddsRange(double minOdds, double maxOdds);

    @Query("SELECT t FROM BettingTicket t JOIN t.selections s WHERE s.fixture.id = ?1")
    List<BettingTicket> findTicketsContainingFixture(Integer fixtureId);

    // Find tickets with at least X selections
    @Query("SELECT t FROM BettingTicket t WHERE SIZE(t.selections) >= ?1")
    List<BettingTicket> findByMinSelectionCount(int minSelections);
}