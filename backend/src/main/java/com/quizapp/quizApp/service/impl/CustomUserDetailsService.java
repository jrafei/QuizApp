package com.quizapp.quizApp.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quizapp.quizApp.repository.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.quizapp.quizApp.model.beans.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));

        // Ajouter le préfixe ROLE_ au rôle
        String role = "ROLE_" + user.getRole().name();

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // Ensure the password is hashed
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(role)))
                .build();
    }
}

