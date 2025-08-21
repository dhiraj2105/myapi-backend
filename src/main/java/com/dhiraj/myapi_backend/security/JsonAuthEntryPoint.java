// ==================================================
// File: JsonAuthEntryPoint.java
// Purpose: Return JSON for 401 (unauthenticated) instead of default HTML.
// Changes:
//  - [Finish] Custom AuthenticationEntryPoint.
// ==================================================

package com.dhiraj.myapi_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JsonAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper(); // JSON mapper. //

    @Override
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