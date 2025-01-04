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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        http.cors().and() // Enable CORS support
                .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
                .authorizeHttpRequests(auth -> auth

                        // *********** Routes publiques *************
                        .requestMatchers(HttpMethod.POST,
                                "/auth/login",
                                "/auth/logout",
                                "/auth/forgot-password",
                                "/auth/reactivation-request",
                                "/auth/reactivate-account",
                                "/users",
                                "/users/{id}"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/auth/activate",
                                "/themes",
                                "/records",
                                "/records/{userId}/stats/themes",
                                "/records/{userId}/stats/quizs/{quizId}",
                                "/records/{userId}/stats/quizs",
                                "/quizzes/{idVersion}")
                        .permitAll()

                        // *********** Routes protégées *************

                        // USERS
//                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN") // Création : uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}").hasAnyRole("ADMIN","TRAINEE") // Modification : ADMIN et TRAINEE
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}/role").hasRole("ADMIN") // Modification du rôle : uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}/activate").hasAnyRole("ADMIN","TRAINEE") // Activation : ADMIN et TRAINEE
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}/deactivate").hasAnyRole("ADMIN","TRAINEE") // Désactivation : ADMIN et TRAINEE
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN") // Suppression : uniquement pour ADMIN

                        // THEMES
                        // .requestMatchers(HttpMethod.GET, "/themes").hasAnyRole("ADMIN", "TRAINEE") // Lister tous les thèmes
                        .requestMatchers(HttpMethod.GET, "/themes/{id}").hasAnyRole("ADMIN", "TRAINEE") // Obtenir un thème par ID

                        .requestMatchers(HttpMethod.POST, "/themes").hasRole("ADMIN") // Création uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/themes/{id}").hasRole("ADMIN") // Modification uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/themes/{id}/activate").hasRole("ADMIN") // Activation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/themes/{id}/deactivate").hasRole("ADMIN") // Désactivation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/themes/{id}").hasRole("ADMIN") // Suppression uniquement pour ADMIN

                        // QUIZZES
                        .requestMatchers(HttpMethod.GET, "/quizzes").hasAnyRole("ADMIN", "TRAINEE") // Lister tous les quizzes
                        .requestMatchers(HttpMethod.GET, "/quizzes/status").hasAnyRole("ADMIN", "TRAINEE") // Filtrer par statut
                        .requestMatchers(HttpMethod.GET, "/quizzes/users/{creatorId}").hasAnyRole("ADMIN", "TRAINEE") // Lister par créateur
                        .requestMatchers(HttpMethod.GET, "/quizzes/theme/{themeId}").hasAnyRole("ADMIN", "TRAINEE") // Lister par thème

                        .requestMatchers(HttpMethod.POST, "/quizzes").hasRole("ADMIN") // Création uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/quizzes/{id}").hasRole("ADMIN") // Modification uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/quizzes/{id}/activate").hasRole("ADMIN") // Activation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/quizzes/{id}/deactivate").hasRole("ADMIN") // Désactivation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/quizzes/{id}").hasRole("ADMIN") // Suppression uniquement pour ADMIN
                        .requestMatchers(HttpMethod.POST, "/quizzes/{idQuiz}").hasRole("ADMIN") // ajout d'une nouvelle version de quiz

                        // QUESTIONS
                        .requestMatchers(HttpMethod.GET, "/questions").hasAnyRole("ADMIN", "TRAINEE") // Liste accessible à ADMIN et TRAINEE
                        .requestMatchers(HttpMethod.POST, "/questions").hasRole("ADMIN") // Création uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PUT, "/questions/{id}").hasRole("ADMIN") // Mise à jour uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/questions/{id}/activate").hasRole("ADMIN") // Activation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/questions/{id}/deactivate").hasRole("ADMIN") // Désactivation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/questions/{id}").hasRole("ADMIN") // Suppression uniquement pour ADMIN

                        // ANSWERS
                        .requestMatchers(HttpMethod.GET, "/answers").hasAnyRole("ADMIN", "TRAINEE") // Liste accessible à ADMIN et TRAINEE
                        .requestMatchers(HttpMethod.POST, "/answers").hasRole("ADMIN") // Création uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/answers/{id}/activate").hasRole("ADMIN") // Activation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/answers/{id}/deactivate").hasRole("ADMIN") // Désactivation uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/answers/{id}/set-correct").hasRole("ADMIN") // Définir une bonne réponse uniquement pour ADMIN
                        .requestMatchers(HttpMethod.PUT, "/answers/{id}").hasRole("ADMIN") // Mise à jour uniquement pour ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/answers/{id}").hasRole("ADMIN") // Suppression uniquement pour ADMIN

                        // RECORDS
                        .requestMatchers(HttpMethod.POST, "/records").hasAnyRole("ADMIN", "TRAINEE") // Création accessible à ADMIN et TRAINEE
                        .requestMatchers(HttpMethod.GET, "/records/completed").hasRole("TRAINEE") // Records complétés accessibles uniquement aux TRAINEE
                        .requestMatchers(HttpMethod.GET, "/records/quizs/{quizId}/leaderboard").hasAnyRole("ADMIN", "TRAINEE") // Leaderboard accessible à ADMIN et TRAINEE
                        .requestMatchers(HttpMethod.POST, "/records/assign").hasRole("ADMIN") // Assignation de quiz uniquement pour ADMIN
                        .requestMatchers(HttpMethod.GET, "/records/pending").hasRole("TRAINEE") // Quizzes assignés accessibles uniquement aux TRAINEE


                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all endpoints
                        .allowedOrigins("http://localhost:5173") // Specify allowed origins
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") // Specify allowed methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials like cookies
            }
        };
    }
}
