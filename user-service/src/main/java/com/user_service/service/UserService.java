package com.user_service.service;

import com.user_service.dto.RegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.entity.User;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse getUserById(Long id);

    User getUserByEmail(String email);

}