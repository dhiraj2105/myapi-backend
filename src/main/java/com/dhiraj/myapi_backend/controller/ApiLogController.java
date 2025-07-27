// ==========================================================================
// File: ApiLogController.java
// Purpose: REST endpoint to retrieve logged API requests from MongoDB.
// Approach:
//   - Provides /logs endpoint with pagination and sorting.
//   - Uses Spring Data MongoRepository methods.
// Changes:
//   - [Init] Created GET /logs endpoint with limit & page query support.
// ==========================================================================

package com.dhiraj.myapi_backend.controller;

import com.dhiraj.myapi_backend.model.ApiLog;
import com.dhiraj.myapi_backend.repository.ApiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class ApiLogController {
    @Autowired
    private ApiLogRepository logRepository;

    // ================================
    // GET /logs?limit=10&page=0
    // ================================
    @GetMapping
    public List<ApiLog> getLogs(
            @RequestParam(defaultValue = "10")int limit,
            @RequestParam(defaultValue = "0") int page
    ){
        return logRepository
                .findAll(PageRequest.of(page,limit,Sort.by(Sort.Direction.DESC,"timestamp")))
                .getContent();
    }
}
