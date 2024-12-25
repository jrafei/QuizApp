package com.quizapp.quizApp.config;

import com.quizapp.quizApp.service.impl.CustomUserDetailsService;
import com.quizapp.quizApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = new JwtUtil();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour une API REST
                .authorizeHttpRequests(auth -> auth
                        // Routes publiques
                        .requestMatchers("/auth/**", "/users/activate").permitAll() // Accessible sans authentification

                        // *********** Routes protégées *************

                        // USERS
                        .requestMatchers(HttpMethod.POST, "/users/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}").hasAnyRole("ADMIN", "TRAINEE") // Stagiaires peuvent modifier leur profil
                        .requestMatchers("/users/**").hasRole("ADMIN") // Admin a un contrôle total sur tous les utilisateurs

                        // THEMES
                        .requestMatchers("/themes/**").hasRole("ADMIN")

                        // QUIZZES
                        .requestMatchers("/quizzes/**").hasRole("ADMIN")

                        // QUESTIONS
                        .requestMatchers("/questions/**").hasRole("ADMIN")

                        // ANSWERS
                        .requestMatchers("/answers/**").hasRole("ADMIN")

                        // RECORDS
                        .requestMatchers("/records/**").hasAnyRole("ADMIN","TRAINEE")

                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}
