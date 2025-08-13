// ==================================================
// File: RateLimitBucket.java
// Purpose: Fixed-window bucket used by rate limiter.
// Changes:
//  - [Finish] Added getters for limit/window to support dynamic re-init.
// ==================================================

package com.dhiraj.myapi_backend.util;

import lombok.Getter;

public class RateLimitBucket {
    private int requestCount; // Current count in window. //
    private long windowStart; // Window start timestamp (ms). //

    // Expose limit. //
    @Getter
    private final int limit; // Max requests per window. //
    // Expose window size. //
    @Getter
    private final long windowSizeMs; // Window duration in ms. //

    public RateLimitBucket(int limit, long windowSizeMs) { // Constructor. //
        this.limit = limit; // Set limit. //
        this.windowSizeMs = windowSizeMs; // Set window size. //
        this.windowStart = System.currentTimeMillis(); // Init start time. //
        this.requestCount = 0; // Init count. //
    }

    public synchronized boolean allowRequest() { // Thread-safe allowance check. //
        long now = System.currentTimeMillis(); // Current time. //
        if (now - windowStart >= windowSizeMs) { // If window expired. //
            windowStart = now; // Reset start. //
            requestCount = 0; // Reset count. //
        }
        if (requestCount < limit) { // Under quota? //
            requestCount++; // Consume a token. //
            return true; // Allow. //
        }
        return false; // Deny. //
    }

}
