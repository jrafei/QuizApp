package com.quizapp.quizApp.config;

import com.quizapp.quizApp.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.extractTokenFromHeader(request);

        if (token != null && jwtUtil.validateToken(token, jwtUtil.extractUsername(token))) {
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);
            String userId = jwtUtil.extractUserId(token);

            // Créer un GrantedAuthority avec le rôle
            GrantedAuthority authority = new SimpleGrantedAuthority(role);

            // Créer un objet d'authentification avec l'autorité (rôle)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));

            // Ajouter l'ID utilisateur comme détail supplémentaire
            authenticationToken.setDetails(userId);

            // Ajouter l'ID utilisateur à la requête
            request.setAttribute("userId", userId);

            // Finaliser l'objet d'authentification
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
