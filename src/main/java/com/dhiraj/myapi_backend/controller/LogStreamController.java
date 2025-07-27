// ==========================================================================
// File: LogStreamController.java
// Purpose: Expose SSE endpoint to stream new logs to the frontend.
// Changes:
//   - [Init] Created GET /stream/logs SSE endpoint.
// ==========================================================================

package com.dhiraj.myapi_backend.controller;

import com.dhiraj.myapi_backend.service.LogStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/stream")
public class LogStreamController {

    @Autowired
    private LogStreamService streamService;

    // =========================================================
    // GET /stream/logs → opens an SSE connection to stream logs
    // =========================================================
    @GetMapping("/logs")
    public SseEmitter streamLogs() {
        return streamService.addEmitter();
    }
}