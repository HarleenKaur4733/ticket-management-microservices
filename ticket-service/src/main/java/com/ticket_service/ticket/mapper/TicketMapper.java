package com.ticket_service.ticket.mapper;

import org.springframework.stereotype.Component;

import com.ticket_service.ticket.dto.CreateTicketRequest;
import com.ticket_service.ticket.dto.TicketResponse;
import com.ticket_service.ticket.entity.Ticket;

@Component
public class TicketMapper {

    public Ticket toEntity(CreateTicketRequest request) {

        if (request == null) {
            return null;
        }

        return Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .build();
    }

    public TicketResponse toResponse(Ticket ticket) {

        if (ticket == null) {
            return null;
        }

        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .priority(ticket.getPriority())
                .createdAt(ticket.getCreatedAt())
                .createdByUserId(ticket.getCreatedByUserId())
                .assignedToUserId(ticket.getAssignedToUserId())
                .build();
    }
}