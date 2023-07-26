package com.auth.userAuthentication.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        // If we have our "userEmail" and the user is not authenticated
        if(userEmail == null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Get the "userDetails" from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Check if the user is valid or not. If the user and the token is valid
            if(jwtService.isTokenValid(jwt, userDetails)) {
                // Create an object of type "UsernamePasswordAuthenticationToken"
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        // Pass in "userDetails", "null", "userDetails.getAuthorities" as parameters
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // Enforce the authentication token with the user details
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Update the authentication token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Call the "filterChain" parameter
        filterChain.doFilter(request, response);
    }
}
