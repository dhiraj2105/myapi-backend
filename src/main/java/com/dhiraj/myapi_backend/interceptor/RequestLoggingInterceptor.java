// ==========================================================================
// File: RequestLoggingInterceptor.java
// Purpose: Intercepts all incoming requests, logs to MongoDB via ApiLogRepository.
// Changes:
//   - [Finish] Sanitize User-Agent to avoid oversized values.
// ==========================================================================
package com.dhiraj.myapi_backend.interceptor;

import com.dhiraj.myapi_backend.model.ApiLog;
import com.dhiraj.myapi_backend.repository.ApiLogRepository;
import com.dhiraj.myapi_backend.service.LogStreamService;
import com.dhiraj.myapi_backend.util.LogSanitizer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    @Autowired
    private ApiLogRepository logRepository;

    @Autowired
    private LogStreamService streamService;

    // Called BEFORE the controller handles the request
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    // Called AFTER the request is completed
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        boolean rateLimited = false;
        Object attr = request.getAttribute("rateLimitExceeded");
        if (attr != null && attr instanceof Boolean) {
            rateLimited = (Boolean) attr;
        }

        String ua = LogSanitizer.sanitizeUserAgent(request.getHeader("User-Agent")); // Sanitize UA. //

        ApiLog log = ApiLog.builder()
                .timestamp(Instant.now())
                .method(request.getMethod())
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .userAgent(ua)
                .status(response.getStatus())
                .responseTimeMs(duration)
                .rateLimitExceeded(rateLimited)
                .build();

        logRepository.save(log);
        // send log to frontend if sse client connected
        streamService.sendLog(log);

        System.out.printf("[SAVED] %s %s (%d) [%dms] Limit: %s%n",
                log.getMethod(), log.getUri(), log.getStatus(), log.getResponseTimeMs(),
                log.isRateLimitExceeded());
    }
}
