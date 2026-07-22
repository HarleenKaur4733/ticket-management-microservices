package com.ticket_service.comment.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket_service.comment.dto.CommentResponse;
import com.ticket_service.comment.dto.CreateCommentRequest;
import com.ticket_service.comment.entity.Comment;
import com.ticket_service.comment.mapper.CommentMapper;
import com.ticket_service.comment.repository.CommentRepository;
import com.ticket_service.ticket.entity.Ticket;
import com.ticket_service.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    // private final TicketRepository ticketRepository;
    // private final UserRepository userRepository;
    private final TicketService ticketService;

    @Override
    @Transactional
    public CommentResponse addComment(Long ticketId,
            CreateCommentRequest request) {

        Ticket ticket = ticketService.getTicketById(ticketId);

        Comment comment = commentMapper.toEntity(request);

        comment.setTicket(ticket);

        // TODO: Get logged-in user from JWT later
        comment.setUserId(null);

        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toResponse(savedComment);
    }

    @Override
    public Page<CommentResponse> getComments(Long ticketId,
            Pageable pageable) {

        return commentRepository
                .findByTicketId(ticketId, pageable)
                .map(commentMapper::toResponse);
    }
}