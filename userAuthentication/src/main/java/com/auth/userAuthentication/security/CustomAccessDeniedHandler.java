package com.auth.userAuthentication.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType ("application/json");
        response.setStatus (HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> data = new HashMap<> ();
        data.put ("status", false);
        data.put ("message", accessDeniedException.getMessage ());
        response.getOutputStream ().print (mapper.writeValueAsString (data));
    }

}
