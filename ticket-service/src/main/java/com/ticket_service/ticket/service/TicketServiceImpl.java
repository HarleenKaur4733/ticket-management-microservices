package com.ticket_service.ticket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ticket_service.client.UserClient;
import com.ticket_service.client.enums.UserResponse;
import com.ticket_service.exception.ResourceNotFoundException;
import com.ticket_service.exception.UnauthorizedException;
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
    private final UserClient userClient;

    private void validateAssignedUser(Long userId) {

        if (userId != null) {
            userClient.getUserById(userId);
        }
    }

    private void validateTicketAccess(
            Ticket ticket,
            Long userId,
            String role) {

        boolean isOwner = ticket.getCreatedByUserId().equals(userId);
        boolean isAdmin = "ADMIN".equals(role);

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedException(
                    "You are not authorized to perform this action.");
        }
    }

    @Override
    public TicketResponse createTicket(CreateTicketRequest request, Long userId) {

        Ticket ticket = ticketMapper.toEntity(request);

        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());

        ticket.setCreatedByUserId(userId);

        // validate assignedToUserId by calling user-service if it exists
        // Validate that assigned user exists
        validateAssignedUser(request.getAssignedToUserId());

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

    @Override
    @CacheEvict(value = "tickets", key = "#id")
    public TicketResponse updateTicket(
            Long id,
            UpdateTicketRequest request,
            Long userId,
            String role) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        validateTicketAccess(ticket, userId, role);

        if (request.getPriority() != null) {
            ticket.setPriority(request.getPriority());
        }

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }

        if (request.getAssignedToUserId() != null) {

            // Validate assigned user exists
            UserResponse assignedUser = userClient.getUserById(request.getAssignedToUserId());

            ticket.setAssignedToUserId(assignedUser.getId());
        }

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(updatedTicket);
    }

    @Override
    @CacheEvict(value = "tickets", key = "#id")
    public void deleteTicket(Long id, Long userId, String role) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        validateTicketAccess(ticket, userId, role);

        ticketRepository.delete(ticket);
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
