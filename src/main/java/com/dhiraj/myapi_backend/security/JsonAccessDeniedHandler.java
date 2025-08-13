// ==================================================
// File: JsonAccessDeniedHandler.java
// Purpose: Return JSON for 403 (forbidden).
// Changes:
//  - [Finish] Custom AccessDeniedHandler.
// ==================================================

package com.dhiraj.myapi_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper = new ObjectMapper(); // Mapper. //

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        response.setStatus(403); // Forbidden. //
        response.setContentType("application/json"); // JSON. //
        mapper.writeValue(response.getWriter(), Map.of( // Body. //
                "error", "forbidden",
                "message", "Access denied",
                "path", request.getRequestURI()
        ));
    }

}
