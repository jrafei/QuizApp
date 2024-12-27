package com.quizapp.quizApp.controller;

import com.quizapp.quizApp.exception.UserNotFoundException;
import com.quizapp.quizApp.model.dto.AuthRequestDTO;
import com.quizapp.quizApp.model.dto.AuthResponseDTO;
import com.quizapp.quizApp.service.impl.CustomUserDetailsService;
import com.quizapp.quizApp.service.interfac.UserService;
import com.quizapp.quizApp.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    // Constructor-based injection
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          CustomUserDetailsService userDetailsService,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @CrossOrigin(origins="http://localhost:5176")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        try {
            System.out.println(authRequest);
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
    @CrossOrigin(origins="http://localhost:5176")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok("If this email is associated with an account, you will receive a message.");
        } catch (UserNotFoundException ex) {
            // Always respond generically to avoid revealing account details
            return ResponseEntity.ok("If this email is associated with an account, you will receive a message.");
        }
    }

    @PostMapping("/reactivation-request")
    public ResponseEntity<String> requestReactivation(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        userService.requestAccountReactivation(email);
        return ResponseEntity.ok("If this email is associated with an inactive account, you will receive a validation code.");
    }

    @PostMapping("/reactivate-account")
    public ResponseEntity<String> reactivateAccount(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String validationCode = requestBody.get("validationCode");

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (validationCode == null || validationCode.isBlank()) {
            throw new IllegalArgumentException("Validation code is required.");
        }

        userService.validateAndReactivateAccount(email, validationCode);
        return ResponseEntity.ok("Your account has been successfully reactivated.");
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam String token) {
        boolean isActivated = userService.activateUserByToken(token);
        if (isActivated) {
            return ResponseEntity.ok("Your account was successfully activated!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid validation token.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Informer le client de supprimer le JWT localement
        return ResponseEntity.ok("User logged out successfully");
    }

}
