package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.model.dto.AuthRequestDTO;
import com.quizapp.quizApp.model.dto.AuthResponseDTO;
import com.quizapp.quizApp.service.impl.CustomUserDetailsService;
import com.quizapp.quizApp.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    // Constructor-based injection
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Extraire les détails de l'utilisateur authentifié
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            // Extraire le rôle de l'utilisateur
            String role = userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .findFirst()
                    .orElse("ROLE_USER");  // Assurez-vous que le rôle existe

            // Generate the JWT
            String token = jwtUtil.generateToken(authRequest.getUsername(), role);

            return ResponseEntity.ok(new AuthResponseDTO(token));
        } catch (AuthenticationException e) {
            // En cas d'échec de l'authentification, retourner un code 401 Unauthorized
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Informer le client de supprimer le JWT localement
        return ResponseEntity.ok("User logged out successfully");
    }

}
