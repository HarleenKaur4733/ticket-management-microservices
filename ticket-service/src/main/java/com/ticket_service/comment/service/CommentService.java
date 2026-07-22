package com.ticket_service.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ticket_service.comment.dto.CommentResponse;
import com.ticket_service.comment.dto.CreateCommentRequest;

public interface CommentService {

        CommentResponse addComment(Long ticketId,
                        CreateCommentRequest request);

        Page<CommentResponse> getComments(Long ticketId,
                        Pageable pageable);

}