// ==========================================================================
// File: WebMvcConfig.java
// Purpose: Registers global interceptors such as API request monitoring.
// Approach:
//   - Extends WebMvcConfigurer to hook custom interceptors.
// Changes:
//   - [Init] Registered RequestLoggingInterceptor globally.
// ==========================================================================

package com.dhiraj.myapi_backend.config;

import com.dhiraj.myapi_backend.interceptor.RequestLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**"); // Apply to all endpoints
    }
}