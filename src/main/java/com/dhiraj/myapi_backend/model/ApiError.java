// ==================================================
// File: ApiError.java
// Purpose: Standard JSON error payload for consistent responses.
// Changes:
//  - [Finish] Introduced simple DTO with code/message/path/timestamp.
// ==================================================

package com.dhiraj.myapi_backend.model;

import java.time.Instant;

public class ApiError {
    public String code;          // Machine-friendly error code. //
    public String message;       // Human-readable message. //
    public String path;          // Request path. //
    public Instant timestamp;    // When error occurred. //

    public ApiError(String code, String message, String path) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
    }
}
