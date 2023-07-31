package com.auth.userAuthentication.config;

/**
 * @author Ernest Emmanuel Utibe
 **/

import com.auth.userAuthentication.data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserDetails userDetails;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Get the authenticated user details
        User userDetails = (User) authentication.getPrincipal();

        // Create and set up user session attributes (you can store any relevant user information in the session)
        HttpSession session = request.getSession();
        session.setAttribute("userId", userDetails.getId());
        session.setAttribute("username", userDetails.getUsername());

        // Generate a JWT token for the authenticated user
        String jwtToken = tokenProvider.generateToken(userDetails);

        // Set the token as a response header or response body, depending on your preference
        response.addHeader("Authorization", "Bearer " + jwtToken);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");

        // Set the response status as OK (200)
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
