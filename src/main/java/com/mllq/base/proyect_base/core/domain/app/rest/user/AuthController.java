package com.mllq.base.proyect_base.core.domain.app.rest.user;

import com.mllq.base.proyect_base.core.domain.app.auth.AuthService;
import com.mllq.base.proyect_base.core.domain.core.dto.request.AuthRequest;
import com.mllq.base.proyect_base.core.domain.core.dto.request.NewUserRequest;
import com.mllq.base.proyect_base.core.domain.core.dto.response.AuthResponse;
import com.mllq.base.proyect_base.core.domain.core.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody NewUserRequest request) {
        // Cambia registerUser a que devuelva UserResponse
        UserResponse userResponse = authService.registerUser(request);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
