package com.babacarmane.studentmanagerbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// security/JwtFilter.java
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
// ↑ OncePerRequestFilter = exécuté UNE seule fois par requête

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 1. Lit le header Authorization
        final String authHeader = request.getHeader("Authorization");

        // 2. Si pas de token → passe à la suite sans authentifier
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrait le token (retire "Bearer ")
        final String jwt = authHeader.substring(7);

        // 4. Extrait l'email depuis le token
        final String email = jwtService.extractEmail(jwt);

        // 5. Si email valide et pas encore authentifié
        if (email != null && SecurityContextHolder.getContext()
                .getAuthentication() == null) {

            // 6. Charge l'utilisateur depuis la base
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 7. Valide le token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 8. Crée l'objet d'authentification
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 9. Met l'utilisateur dans le SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // ↑ à partir d'ici Spring sait qui fait la requête
            }
        }

        // 10. Continue vers le Controller
        filterChain.doFilter(request, response);
    }
}