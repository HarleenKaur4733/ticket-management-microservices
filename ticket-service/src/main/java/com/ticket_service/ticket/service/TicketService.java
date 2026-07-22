package com.ticket_service.ticket.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ticket_service.ticket.dto.CreateTicketRequest;
import com.ticket_service.ticket.dto.TicketDashboardResponse;
import com.ticket_service.ticket.dto.TicketResponse;
import com.ticket_service.ticket.dto.UpdateTicketRequest;
import com.ticket_service.ticket.entity.Ticket;

public interface TicketService {

    TicketResponse createTicket(CreateTicketRequest request);

    TicketResponse getTicket(Long id);

    Ticket getTicketById(Long id);

    Page<TicketResponse> getAllTickets(Pageable pageable);

    TicketResponse updateTicket(Long id, UpdateTicketRequest request);

    void deleteTicket(Long id);

    List<TicketDashboardResponse> getDashboardTickets();

}