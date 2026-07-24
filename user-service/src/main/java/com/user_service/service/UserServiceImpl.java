package com.user_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user_service.dto.RegisterRequest;
import com.user_service.dto.UserResponse;
import com.user_service.entity.Role;
import com.user_service.entity.User;
import com.user_service.exception.ResourceNotFoundException;
import com.user_service.mapper.UserMapper;
import com.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {
        // Implementation for user registration
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = userMapper.toEntity(request);
        user.setRole(Role.USER); // Set default role to USER);
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        // Save user to database
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public User getUserById(Long id) {
        // Implementation to retrieve user by ID
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}