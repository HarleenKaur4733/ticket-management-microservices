package com.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user_service.dto.RegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody RegisterRequest request) {

        UserResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
