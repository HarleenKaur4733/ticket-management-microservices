package com.ticket_service.comment.mapper;

import org.springframework.stereotype.Component;

import com.ticket_service.comment.dto.CommentResponse;
import com.ticket_service.comment.dto.CreateCommentRequest;
import com.ticket_service.comment.entity.Comment;

@Component
public class CommentMapper {

    public Comment toEntity(CreateCommentRequest request) {

        if (request == null) {
            return null;
        }

        return Comment.builder()
                .content(request.getContent())
                .build();
    }

    public CommentResponse toResponse(Comment comment) {

        if (comment == null) {
            return null;
        }

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}