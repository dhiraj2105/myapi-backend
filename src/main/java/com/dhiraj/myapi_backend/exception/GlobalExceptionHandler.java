// ==================================================
// File: GlobalExceptionHandler.java
// Purpose: Return consistent JSON errors for exceptions across controllers.
// Approach: @RestControllerAdvice with specific handlers + fallback.
// Changes:
//  - [Finish] Added handlers for IllegalArgumentException, RuntimeException, generic Exception.
// ==================================================

package com.dhiraj.myapi_backend.exception;

import com.dhiraj.myapi_backend.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class) // Handle 400-like issues. //
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex, HttpServletRequest req) {
        ApiError err = new ApiError("bad_request", ex.getMessage(), req.getRequestURI()); // Create error. //
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // 400 JSON. //
    }

    @ExceptionHandler(RuntimeException.class) // Generic runtime. //
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest req) {
        ApiError err = new ApiError("runtime_error", ex.getMessage(), req.getRequestURI()); // Error. //
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err); // 500 JSON. //
    }

    @ExceptionHandler(Exception.class) // Catch-all. //
    public ResponseEntity<ApiError> handleAny(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError("server_error", "Unexpected error", req.getRequestURI()); // Sanitize. //
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err); // 500 JSON. //
    }
}
