// ==========================================================================
// File: RateLimitBucket.java
// Purpose: Represents a rate limit window for a client/IP.
// Approach:
//   - Tracks request count and window start time.
//   - Resets automatically after window expires.
// Changes:
//   - [Init] Created fixed window rate limit bucket.
// ==========================================================================

package com.dhiraj.myapi_backend.util;

public class RateLimitBucket {
    private int requestCount;
    private long windowStart;

    private final int limit;
    private final long windowSizeMs;

    public RateLimitBucket(int limit, long windowSizeMs){
        this.limit = limit;
        this.windowSizeMs = windowSizeMs;
        this.windowStart = System.currentTimeMillis();
        this.requestCount = 0;
    }

    public synchronized boolean allowRequest(){
        long now = System.currentTimeMillis();

//        check if window expired
        if(now - windowStart >= windowSizeMs){
            windowStart = now;
            requestCount = 0;
        }
        if(requestCount < limit){
            requestCount++;
            return true;
        }else{
            return false;
        }
    }
}
