// ==========================================================================
// File: ApiLogRepository.java
// Purpose: Spring Data interface for interacting with the MongoDB collection.
// Approach:
//   - Uses MongoRepository for automatic CRUD methods.
//   - Collection: api_logs
// Changes:
//   - [Init] Created repository interface for ApiLog.
// ==========================================================================

package com.dhiraj.myapi_backend.repository;

import com.dhiraj.myapi_backend.model.ApiLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiLogRepository extends MongoRepository<ApiLog,String> {
}
