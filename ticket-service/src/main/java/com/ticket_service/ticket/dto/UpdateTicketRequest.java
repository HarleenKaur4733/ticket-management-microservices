package com.ticket_service.ticket.dto;

import com.ticket_service.ticket.entity.TicketPriority;
import com.ticket_service.ticket.entity.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTicketRequest {

    private TicketStatus status;

    private TicketPriority priority;

    private Long assignedToUserId;

}