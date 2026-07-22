package com.ticket_service.ticket.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TicketDashboardResponse {

    private Long id;

    private String title;

    private Long assignedToUserId;
}