// ==========================================================================
// File: ApiLog.java
// Purpose: MongoDB document representing one API request log.
// Changes:
//   - [Added] rateLimitExceeded boolean flag to track blocked requests.
// ==========================================================================

package com.dhiraj.myapi_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "api_logs")
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

    private boolean rateLimitExceeded;
}
