package com.ticket_service.ticket.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket_service.ticket.dto.CreateTicketRequest;
import com.ticket_service.ticket.dto.TicketDashboardResponse;
import com.ticket_service.ticket.dto.TicketResponse;
import com.ticket_service.ticket.dto.UpdateTicketRequest;
import com.ticket_service.ticket.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

        private final TicketService ticketService;

        @PostMapping
        public ResponseEntity<TicketResponse> createTicket(@RequestHeader("X-User-Id") Long userId,
                        @RequestBody CreateTicketRequest request) {

                TicketResponse response = ticketService.createTicket(request, userId);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<TicketResponse> getTicket(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                ticketService.getTicket(id));
        }

        @GetMapping
        public ResponseEntity<Page<TicketResponse>> getAllTickets(
                        Pageable pageable) {

                return ResponseEntity.ok(
                                ticketService.getAllTickets(pageable));
        }

        @PutMapping("/{id}")
        public ResponseEntity<TicketResponse> updateTicket(
                        @PathVariable Long id,
                        @RequestHeader("X-User-Id") Long userId,
                        @RequestHeader("X-User-Role") String role,
                        @Valid @RequestBody UpdateTicketRequest request) {

                return ResponseEntity.ok(
                                ticketService.updateTicket(id, request, userId, role));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTicket(
                        @PathVariable Long id,
                        @RequestHeader("X-User-Id") Long userId,
                        @RequestHeader("X-User-Role") String role) {

                ticketService.deleteTicket(id, userId, role);

                return ResponseEntity.noContent().build();
        }

        @GetMapping("/dashboard")
        public ResponseEntity<List<TicketDashboardResponse>> getDashboard() {

                return ResponseEntity.ok(
                                ticketService.getDashboardTickets());
        }
}