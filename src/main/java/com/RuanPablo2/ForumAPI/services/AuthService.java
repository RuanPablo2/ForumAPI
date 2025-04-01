package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.dtos.request.LoginRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.LoginResponseDTO;
import com.RuanPablo2.ForumAPI.exception.UnauthorizedException;
import com.RuanPablo2.ForumAPI.security.CustomUserDetails;
import com.RuanPablo2.ForumAPI.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String jwt = jwtUtil.generateToken(userDetails.getEmail(), userDetails.getRole().toString());

            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(jwt);
            response.setUserId(userDetails.getId());
            response.setName(userDetails.getName());

            return response;
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid username or password", "AUTH-001", e);
        }
    }
}