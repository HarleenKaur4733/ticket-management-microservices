package com.ticket_service.ticket.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ticket_service.ticket.entity.TicketPriority;
import com.ticket_service.ticket.entity.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Long id;

    private String title;

    private String description;

    private TicketStatus status;

    private TicketPriority priority;

    private Long createdByUserId;

    private Long assignedToUserId;

    private LocalDateTime createdAt;
}