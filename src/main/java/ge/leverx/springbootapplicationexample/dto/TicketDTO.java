package ge.leverx.springbootapplicationexample.dto;

import ge.leverx.springbootapplicationexample.entities.TicketType;

public class TicketDTO {
    public record TicketRequest(
            String title,
            String description,
            TicketType ticketType
    ) {
    }

    public record TicketResponse(
            Long id,
            String title,
            String description,
            TicketType ticketType,
            java.time.LocalDateTime createdAt
    ) {}
}
