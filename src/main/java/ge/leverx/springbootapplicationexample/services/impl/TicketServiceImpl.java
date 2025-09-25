package ge.leverx.springbootapplicationexample.services.impl;

import ge.leverx.springbootapplicationexample.dto.TicketDTO;
import ge.leverx.springbootapplicationexample.entities.Ticket;
import ge.leverx.springbootapplicationexample.entities.TicketType;
import ge.leverx.springbootapplicationexample.repository.TicketRepository;
import ge.leverx.springbootapplicationexample.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public TicketDTO.TicketResponse createTicket(TicketDTO.TicketRequest ticketDTO) {
        var model = modelMapper.map(ticketDTO, Ticket.class);
        var saved = ticketRepository.save(model);
        return modelMapper.map(saved, TicketDTO.TicketResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public TicketDTO.TicketResponse getTicket(Long id) {
        var t = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + id));

        return modelMapper.map(t, TicketDTO.TicketResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TicketDTO.TicketResponse> getAll() {
        return ticketRepository.findAll()
                .stream()
                .map(t -> modelMapper.map(t, TicketDTO.TicketResponse.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketDTO.TicketResponse> getByDescriptionAndTitle(String description, String title) {
        return ticketRepository.findByDescriptionAndTitle(description, title)
                .stream()
                .map(t -> modelMapper.map(t, TicketDTO.TicketResponse.class))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TicketDTO.TicketResponse> getRecentByTypeJPQL(String typeStr) {
        var since = LocalDateTime.now().minusYears(1);
        var type = TicketType.valueOf(typeStr);
        return ticketRepository.findRecentByTypeJPQL(type, since).stream()
                .map(t -> modelMapper.map(t, TicketDTO.TicketResponse.class))
                .toList();
    }


    @Transactional(readOnly = true)
    @Override
    public List<TicketDTO.TicketResponse> getRecentByTypeNative(String typeStr) {
        var since = LocalDateTime.now().minusYears(1);
        return ticketRepository.findRecentByTypeNative(typeStr, since).stream()
                .map(t -> modelMapper.map(t, TicketDTO.TicketResponse.class))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TicketDTO.TicketResponse> getRecentByTypeDerived(String typeStr) {
        var type = TicketType.valueOf(typeStr.toUpperCase());
        var since = LocalDateTime.now().minusYears(1);

        return ticketRepository
                .findByTicketTypeAndCreatedAtGreaterThanEqual(type, since)
                .stream()
                .map(t -> modelMapper.map(t, TicketDTO.TicketResponse.class))
                .toList();
    }

    @Transactional
    @Override
    public TicketDTO.TicketResponse updateTicket(TicketDTO.TicketRequest ticketDTO, Long id) {
        var existing = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + id));

        if (nonNull(ticketDTO.title())) {
            existing.setTitle(ticketDTO.title());
        }
        if (nonNull(ticketDTO.description())) {
            existing.setDescription(ticketDTO.description());
        }

        var saved = ticketRepository.save(existing);
        return modelMapper.map(saved, TicketDTO.TicketResponse.class);
    }

    @Transactional
    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
