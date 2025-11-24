package com.tbd.lab1tbd.Controllers;

import com.tbd.lab1tbd.Auth.AuthenticationRequest;
import com.tbd.lab1tbd.Auth.AuthenticationResponse;
import com.tbd.lab1tbd.Auth.AuthenticationService;
import com.tbd.lab1tbd.Auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    /**
     * Endpoint público para registrar un nuevo usuario.
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Endpoint público para autenticar (loguear) un usuario.
     * POST /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
