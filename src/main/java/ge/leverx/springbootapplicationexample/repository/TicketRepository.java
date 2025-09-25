package ge.leverx.springbootapplicationexample.repository;

import ge.leverx.springbootapplicationexample.entities.Ticket;
import ge.leverx.springbootapplicationexample.entities.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByDescriptionAndTitle(String description, String title);

    @Query("select t from Ticket t where t.ticketType = :type and t.createdAt >= :since")
    List<Ticket> findRecentByTypeJPQL(@Param("type") TicketType type,
                                      @Param("since") LocalDateTime since);

    @Query(
            value = "SELECT * FROM tickets WHERE ticket_type = :type AND created_at >= :since",
            nativeQuery = true
    )
    List<Ticket> findRecentByTypeNative(@Param("type") String type,
                                        @Param("since") LocalDateTime since);

    List<Ticket> findByTicketTypeAndCreatedAtGreaterThanEqual(
            TicketType ticketType,
            LocalDateTime since
    );
}
