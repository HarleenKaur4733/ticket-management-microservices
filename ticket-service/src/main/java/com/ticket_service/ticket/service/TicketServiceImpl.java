package com.ticket_service.ticket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ticket_service.exception.ResourceNotFoundException;
import com.ticket_service.ticket.dto.CreateTicketRequest;
import com.ticket_service.ticket.dto.TicketDashboardResponse;
import com.ticket_service.ticket.dto.TicketResponse;
import com.ticket_service.ticket.dto.UpdateTicketRequest;
import com.ticket_service.ticket.entity.Ticket;
import com.ticket_service.ticket.entity.TicketStatus;
import com.ticket_service.ticket.mapper.TicketMapper;
import com.ticket_service.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {

        Ticket ticket = ticketMapper.toEntity(request);

        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());

        // TODO: Get from JWT later
        ticket.setCreatedByUserId(null);

        ticket.setAssignedToUserId(request.getAssignedToUserId());

        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(savedTicket);
    }

    @Cacheable(value = "tickets", key = "#id")
    @Override
    public TicketResponse getTicket(Long id) {
        System.out.println("Fetching from Database...");
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        return ticketMapper.toResponse(ticket);
    }

    @Override
    public Page<TicketResponse> getAllTickets(Pageable pageable) {

        return ticketRepository.findAll(pageable)
                .map(ticketMapper::toResponse);

    }

    @CacheEvict(value = "tickets", key = "#id")
    @Override
    public TicketResponse updateTicket(Long id, UpdateTicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if (request.getPriority() != null) {
            ticket.setPriority(request.getPriority());
        }
        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }
        if (request.getAssignedToUserId() != null) {
            ticket.setAssignedToUserId(
                    request.getAssignedToUserId());
        }

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(updatedTicket);

    }

    @CacheEvict(value = "tickets", key = "#id")
    @Override
    public void deleteTicket(Long id) {
        ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticketRepository.deleteById(id);
    }

    @Cacheable(value = "ticketEntities", key = "#id")
    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }

    @Override
    public List<TicketDashboardResponse> getDashboardTickets() {

        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .map(ticket -> TicketDashboardResponse.builder()
                        .id(ticket.getId())
                        .title(ticket.getTitle())
                        .assignedToUserId(ticket.getAssignedToUserId())
                        .build())
                .toList();
    }

}
