package ge.leverx.springbootapplicationexample.controllers;

import ge.leverx.springbootapplicationexample.dto.TicketDTO;
import ge.leverx.springbootapplicationexample.logging.CustomLogging;
import ge.leverx.springbootapplicationexample.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

//    @CustomLogging(level =  CustomLogging.Level.INFO, logArgs = false)
    @PostMapping("/create")
    public ResponseEntity<TicketDTO.TicketResponse> createTicket(@RequestBody TicketDTO.TicketRequest ticketDTO) {
        var ticket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO.TicketResponse> getTicket(@PathVariable Long id) {
        var ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO.TicketResponse>> getAllTickets() {
        var tickets = ticketService.getAll();
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TicketDTO.TicketResponse> updateTicket(@RequestBody TicketDTO.TicketRequest ticketDTO,
                                                                 @PathVariable Long id) {
        var ticket = ticketService.updateTicket(ticketDTO, id);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<TicketDTO.TicketResponse>> searchByDescriptionAndTitle(@RequestParam String description,
                                                                                      @RequestParam String title) {
        var byDescriptionAndTitle = ticketService.getByDescriptionAndTitle(description, title);
        return ResponseEntity.ok(byDescriptionAndTitle);
    }

    @GetMapping("/recent/jpql")
    public ResponseEntity<List<TicketDTO.TicketResponse>> getRecentByTypeJPQL(@RequestParam String type) {
        var recentByTypeJPQL = ticketService.getRecentByTypeJPQL(type);
        return ResponseEntity.ok(recentByTypeJPQL);
    }

    @GetMapping("/recent/native")
    public ResponseEntity<List<TicketDTO.TicketResponse>> getRecentByTypeNative(@RequestParam String type) {
        var recentByTypeNative = ticketService.getRecentByTypeNative(type);
        return ResponseEntity.ok(recentByTypeNative);
    }

    @GetMapping("/recent/derived")
    public ResponseEntity<List<TicketDTO.TicketResponse>> getRecentByTypeDerived(@RequestParam String type) {
        var recentByTypeDerived = ticketService.getRecentByTypeDerived(type);
        return ResponseEntity.ok(recentByTypeDerived);
    }
}

