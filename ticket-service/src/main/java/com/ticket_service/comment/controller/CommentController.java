package com.ticket_service.comment.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticket_service.comment.dto.CommentResponse;
import com.ticket_service.comment.dto.CreateCommentRequest;
import com.ticket_service.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets/{ticketId}/comments")
@RequiredArgsConstructor
public class CommentController {

        private final CommentService commentService;

        @PostMapping
        public ResponseEntity<CommentResponse> addComment(

                        @PathVariable Long ticketId,

                        @RequestBody CreateCommentRequest request) {

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(commentService.addComment(ticketId,
                                                request));
        }

        @GetMapping
        public ResponseEntity<Page<CommentResponse>> getComments(
                        @PathVariable Long ticketId,
                        Pageable pageable) {

                return ResponseEntity.ok(
                                commentService.getComments(ticketId, pageable));
        }
}