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

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
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

                        // *********** Routes publiques *************

                        .requestMatchers(
                                HttpMethod.POST,
                                "/auth/**",
                                "/users/forgot-password",
                                "/users/reactivation-request",
                                "/users/reactivate-account",
                                "/users/activate"
                        ).permitAll()

                        // *********** Routes protégées *************

                        // USERS
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
                        //.requestMatchers("/records/**").hasAnyRole("ADMIN","TRAINEE")
                        .requestMatchers(HttpMethod.POST, "/records/assign").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/records/pending").hasRole("TRAINEE")
                        .requestMatchers(HttpMethod.GET, "/records/completed").hasRole("TRAINEE")


                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

/*
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    System.out.println("In Security.config");
    http
            .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for simplicity
            .addFilterBefore(customCorsFilter, UsernamePasswordAuthenticationFilter.class) // Ajout du filtre CORS
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/auth/login").permitAll() // Allow access to /auth/login for everyone
                    .requestMatchers("/users/all").permitAll() // Allow access to /auth/login for everyone
                    .requestMatchers("/users/{userId}/stats").permitAll()
                    .requestMatchers("/themes/{themeId}/quizzes").permitAll()
                    .requestMatchers("/themes/all").permitAll()
                    .requestMatchers("/quizzes/{quizId}/stats").permitAll()

                    .requestMatchers("/quizzes").hasAnyRole("admin", "trainee")

                    .requestMatchers("/quizzes/user/{userId}").hasRole("admin")
                    .requestMatchers("/quizzes/active").hasRole("admin")
                    .requestMatchers("/quizzes/{quizId}/status").hasRole("admin")
                    .requestMatchers("/quizzes/{quizId}").hasRole("admin")
                    .requestMatchers("/quizzes/user/{userId}/created").hasRole("admin")
                    .requestMatchers("/quizzes/{quizId}/versions").hasRole("admin")
                    .requestMatchers("/quizzes/{quizId}/latest").hasRole("admin")

                    .requestMatchers("/users").hasRole("admin")
                    .requestMatchers("/users/{id}").hasRole("admin")
                    .requestMatchers("/users/{id}/status").hasRole("admin")

                    .requestMatchers("/answers").hasRole("trainee")
                    .requestMatchers("/answers/{id}").hasRole("admin")
                    .requestMatchers("/answers/question/{questionId}").hasRole("admin")
                    .requestMatchers("/answers/{answerId}/position").hasRole("admin")
                    .requestMatchers("/answers/{id}/status").hasRole("admin")

                    .requestMatchers("/questions/**").hasRole("admin")

                    .requestMatchers("/records/users/{userId}/quizzes/{quizId}").hasRole("trainee")
                    .requestMatchers("/records/users/{userId}").hasRole("admin")
                    .requestMatchers("/records/quizzes/{quizId}").hasRole("admin")

                    .requestMatchers("/records/{recordId}").hasAnyRole("admin", "trainee")
                    .requestMatchers("/records/{recordId}/answers").hasAnyRole("admin", "trainee")

                    .requestMatchers("/records/{recordId}/delete").hasRole("admin")

                    .requestMatchers("/themes").hasRole("admin")
                    .requestMatchers("/themes/{themeId}").hasRole("admin")

                    //.requestMatchers("/users").hasRole("admin")
                    .anyRequest().authenticated() // Require authentication for all other endpoints
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before username/password authentication

    return http.build();
 */
