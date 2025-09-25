package ge.leverx.springbootapplicationexample.configs;

import ge.leverx.springbootapplicationexample.dto.TicketDTO;
import ge.leverx.springbootapplicationexample.entities.Ticket;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // Entity -> Record (constructor mapping for record)
        Converter<Ticket, TicketDTO.TicketResponse> toResponse = ctx -> {
            Ticket e = ctx.getSource();
            if (e == null) return null;
            return new TicketDTO.TicketResponse(
                    e.getId(),
                    e.getTitle(),
                    e.getDescription(),
                    e.getTicketType(),
                    e.getCreatedAt()
            );
        };

        TypeMap<Ticket, TicketDTO.TicketResponse> typeMap =
                modelMapper.createTypeMap(Ticket.class, TicketDTO.TicketResponse.class);
        typeMap.setConverter(toResponse);

        TypeMap<TicketDTO.TicketRequest, Ticket> reqMap =
                modelMapper.createTypeMap(TicketDTO.TicketRequest.class, Ticket.class);
        reqMap.setConverter(ctx -> {
            var r = ctx.getSource();
            var t = new Ticket();
            t.setTitle(r.title());
            t.setDescription(r.description());
            t.setTicketType(r.ticketType());
            return t;
        });

        return modelMapper;
    }
}
