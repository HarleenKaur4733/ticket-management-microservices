package com.ticket_service.ticket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket_service.ticket.entity.Ticket;
import com.ticket_service.ticket.entity.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByStatus(
            TicketStatus status,
            Pageable pageable);

}