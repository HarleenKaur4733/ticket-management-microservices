package com.user_service.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.user_service.auth.dto.LoginRequest;
import com.user_service.auth.dto.LoginResponse;
import com.user_service.auth.security.JwtService;
import com.user_service.entity.User;
import com.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        // Step 1: Authenticate email & password
        Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // Step 2: Get authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Step 3: Fetch actual User entity
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 4: Generate JWT with custom claims
        String token = jwtService.generateToken(
                userDetails,
                user.getId(),
                user.getRole().name());

        // Step 5: Return token
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}