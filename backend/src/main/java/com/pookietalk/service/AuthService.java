package com.pookietalk.service;

import com.pookietalk.dto.request.LoginRequest;
import com.pookietalk.dto.request.RegisterRequest;
import com.pookietalk.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void logout(String token);
    AuthResponse refreshToken(String refreshToken);
}