package com.ticket_service.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket_service.comment.entity.Comment;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {

    Page<Comment> findByTicketId(Long ticketId, Pageable pageable);
}