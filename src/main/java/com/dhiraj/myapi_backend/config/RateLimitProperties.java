// ==================================================
// File: RateLimitProperties.java
// Purpose: Bind rate limiting settings from application.properties.
// Approach: @ConfigurationProperties to map rate.limit.* into fields.
// Changes:
//  - [Finish] Introduced typed config bean for limit + window.
// ==================================================

package com.dhiraj.myapi_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "rate.limit")
public class RateLimitProperties {
    private int limit;
    private long windowMs;

}
