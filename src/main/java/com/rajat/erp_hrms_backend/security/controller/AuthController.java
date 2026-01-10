package com.rajat.erp_hrms_backend.security.controller;

import com.rajat.erp_hrms_backend.common.dto.ApiResponse;
import com.rajat.erp_hrms_backend.security.dto.LoginRequest;
import com.rajat.erp_hrms_backend.security.dto.LoginResponse;
import com.rajat.erp_hrms_backend.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getUsername());

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return new ApiResponse<>(true, "Login successful", response);
    }
}
