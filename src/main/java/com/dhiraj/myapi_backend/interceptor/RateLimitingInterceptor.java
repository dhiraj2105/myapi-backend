// ==================================================
// File: RateLimitingInterceptor.java
// Purpose: Apply IP-based rate limiting with config-driven thresholds.
// Approach: Read limits from RateLimitProperties instead of hardcoding.
// Changes:
//  - [Finish] Injected RateLimitProperties.
// ==================================================

package com.dhiraj.myapi_backend.interceptor;

import com.dhiraj.myapi_backend.config.RateLimitProperties;
import com.dhiraj.myapi_backend.util.RateLimitBucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

//    private static final int REQUEST_LIMIT = 10;
//    private static final long WINDOW_MS = 15 * 60 * 1000; // 15 minutes

    private final ConcurrentHashMap<String, RateLimitBucket> buckets = new ConcurrentHashMap<>();
    private final RateLimitProperties props;

    public RateLimitingInterceptor(RateLimitProperties props) {
        this.props = props;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws java.io.IOException { // Intercept before controller. //
        String ip = request.getRemoteAddr(); // Identify client. //
        // Build bucket lazily per IP with current config (limit+window). //
        RateLimitBucket bucket = buckets.compute(ip, (k, existing) -> {
            if (existing == null) { // If no bucket yet. //
                return new RateLimitBucket(props.getLimit(), props.getWindowMs()); // New bucket. //
            }
            // If config changed at runtime (rare), recreate with new values. //
            if (existing.getLimit() != props.getLimit() || existing.getWindowSizeMs() != props.getWindowMs()) {
                return new RateLimitBucket(props.getLimit(), props.getWindowMs()); // Re-init. //
            }
            return existing; // Keep bucket. //
        });

        if (!bucket.allowRequest()) { // Quota exceeded? //
            request.setAttribute("rateLimitExceeded", true); // Mark for logger. //
            response.setStatus(429); // Too Many Requests. //
            response.setContentType("application/json"); // JSON response. //
            response.getWriter().write("{\"error\":\"too_many_requests\",\"message\":\"Rate limit exceeded\"}"); // Body. //
            return false; // Stop chain. //
        }

        request.setAttribute("rateLimitExceeded", false); // Not rate-limited. //
        return true; // Continue to next interceptor/controller. //
    }
}