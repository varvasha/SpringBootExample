package ge.leverx.springbootapplicationexample.dto;

public class TicketDTO {
    public record TicketRequest(String title, String description){}

    public record TicketResponse(Long id, String title, String description){}
}
