package com.mllq.base.proyect_base.core.domain.app.auth;

import com.mllq.base.proyect_base.core.commons.libs.auth.jwt.JwtUtil;
import com.mllq.base.proyect_base.core.commons.models.enums.Role;
import com.mllq.base.proyect_base.core.domain.app.bussines.service.user.UserService;

import com.mllq.base.proyect_base.core.domain.core.dto.request.NewUserRequest;
import com.mllq.base.proyect_base.core.domain.core.dto.response.UserResponse;
import com.mllq.base.proyect_base.core.domain.core.entities.RoleEntity;
import com.mllq.base.proyect_base.core.domain.core.entities.User;
import com.mllq.base.proyect_base.core.domain.core.repo.role.RoleRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthService(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        return jwtUtil.generateToken(authResult);
    }

    public UserResponse registerUser(NewUserRequest request) {
        if(userService.existsByUsername(request.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }

        RoleEntity roleUser = roleRepository.findByName(Role.USER).orElseThrow(()->new RuntimeException("Rol no encontrado"));
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleUser);

         User savedUser = userService.save(user);

        return new UserResponse(savedUser.getUserId(), savedUser.getEmail(), savedUser.getRole().getName());
    }
}
