package com.user_service.auth.service;

import com.user_service.auth.dto.LoginRequest;
import com.user_service.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}