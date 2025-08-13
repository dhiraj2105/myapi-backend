// ==================================================
// File: JsonAuthEntryPoint.java
// Purpose: Return JSON for 401 (unauthenticated) instead of default HTML.
// Changes:
//  - [Finish] Custom AuthenticationEntryPoint.
// ==================================================

package com.dhiraj.myapi_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Map;

@Component
public abstract class JsonAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper(); // JSON mapper. //

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(401); // 401 Unauthorized. //
        response.setContentType("application/json"); // JSON. //
        mapper.writeValue(response.getWriter(), Map.of( // Write body. //
                "error", "unauthorized",
                "message", "Authentication required",
                "path", request.getRequestURI()
        ));
    }
}
