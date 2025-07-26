// ==========================================================================
// File: ApiLog.java
// Purpose: MongoDB document representing one API request log.
// Approach:
//   - Captures essential metadata: timestamp, URI, method, IP, status, etc.
//   - Mapped to 'api_logs' collection in MongoDB.
// Changes:
//   - [Init] Created API log document schema.
// ==========================================================================

package com.dhiraj.myapi_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collation = "api_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiLog {
    @Id
    private String id;

    private Instant timestamp;

    private String method;
    private String uri;
    private String ip;
    private String userAgent;

    private int status;
    private long responseTimeMs;
}
