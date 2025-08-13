// ==========================================================================
// File: RateLimitingInterceptor.java
// Purpose: Globally rate limits clients based on IP using in-memory buckets.
// Approach:
//   - Uses a ConcurrentHashMap to store buckets per IP.
//   - Applies a simple fixed window algorithm.
// Changes:
//   - [Added] Sets rateLimitExceeded=true if blocked.
// ==========================================================================

package com.dhiraj.myapi_backend.interceptor;

import com.dhiraj.myapi_backend.util.RateLimitBucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private static final int REQUEST_LIMIT = 10;
    private static final long WINDOW_MS = 15 * 60 * 1000; // 15 minutes

    private final ConcurrentHashMap<String, RateLimitBucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String ip = request.getRemoteAddr();
        RateLimitBucket bucket = buckets.computeIfAbsent(ip, k -> new RateLimitBucket(REQUEST_LIMIT, WINDOW_MS));

        if (!bucket.allowRequest()) {
            request.setAttribute("rateLimitExceeded", true); // pass to logger
            response.setStatus(429);
            response.getWriter().write("Too many requests. Please try again later.");
            System.out.printf("[RATE LIMIT] Blocked IP %s%n", ip);
            return false;
        }

        request.setAttribute("rateLimitExceeded", false);
        return true;
    }
}