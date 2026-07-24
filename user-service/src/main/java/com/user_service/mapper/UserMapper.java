package com.user_service.mapper;

import org.springframework.stereotype.Component;

import com.user_service.dto.RegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.entity.User;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request) {

        if (request == null) {
            return null;
        }

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}