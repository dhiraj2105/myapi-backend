// ==========================================================================
// File: RequestLoggingInterceptor.java
// Purpose: Intercepts all incoming HTTP requests for monitoring/logging.
// Approach:
//   - Uses HandlerInterceptor to capture details pre-controller.
//   - Measures processing time and logs basic request metadata.
// Changes:
//   - [Init] Basic request interceptor created.
// ==========================================================================
// ==========================================================================
// File: RequestLoggingInterceptor.java
// Purpose: Intercepts all incoming HTTP requests for monitoring/logging.
// Approach:
//   - Uses HandlerInterceptor to capture details pre-controller.
//   - Measures processing time and logs basic request metadata.
// Changes:
//   - [Init] Basic request interceptor created.
// ==========================================================================
package com.dhiraj.myapi_backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    // Called BEFORE the controller handles the request
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime); // Store start time

        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");

        System.out.printf("[API MONITOR] Incoming request: %s %s from IP: %s UA: %s%n", method, uri, ip, userAgent);
        return true;
    }

    // Called AFTER the request is completed
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        int status = response.getStatus();
        String uri = request.getRequestURI();

        System.out.printf("[API MONITOR] Response for %s → Status: %d | Time: %dms%n", uri, status, duration);
    }
}
