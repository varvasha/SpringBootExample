package ge.leverx.springbootapplicationexample.services;

import ge.leverx.springbootapplicationexample.dto.TicketDTO;
import ge.leverx.springbootapplicationexample.entities.TicketType;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {
    TicketDTO.TicketResponse createTicket(TicketDTO.TicketRequest ticketDTO);

    TicketDTO.TicketResponse getTicket(Long id);

    List<TicketDTO.TicketResponse> getAll();

    TicketDTO.TicketResponse updateTicket(TicketDTO.TicketRequest ticketDTO, Long id);

    void deleteTicket(Long id);

    List<TicketDTO.TicketResponse> getByDescriptionAndTitle(String description, String title);

    List<TicketDTO.TicketResponse> getRecentByTypeJPQL(String ticketType);

    List<TicketDTO.TicketResponse> getRecentByTypeNative(String ticketType);

    List<TicketDTO.TicketResponse> getRecentByTypeDerived(String ticketType);

}
