// ==================================================
// File: LogSanitizer.java
// Purpose: Best-effort masking of sensitive header values.
// Approach: Mask common secret-like headers before persisting.
// Changes:
//  - [Finish] Added helper to mask Authorization-like headers.
// ==================================================

package com.dhiraj.myapi_backend.util;

import jakarta.servlet.http.HttpServletRequest;

public class LogSanitizer {
    public static String sanitizeUserAgent(String ua) { // Keep UA as-is or truncate if huge. //
        if (ua == null) return null; // Null guard. //
        return ua.length() > 512 ? ua.substring(0, 512) : ua; // Truncate long UA. //
    }

    public static String sanitizeAuthHeader(String auth) { // Mask tokens if present. //
        if (auth == null) return null; // No header. //
        int n = Math.min(auth.length(), 8); // Keep first few chars. //
        return auth.substring(0, n) + "...(masked)"; // Mask tail. //
    }
}
