// ==========================================================================
// File: AuthController.java
// Purpose: Basic login endpoint that returns a JWT token.
// ==========================================================================

package com.dhiraj.myapi_backend.controller;

import com.dhiraj.myapi_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");

//        temp: hardcoded check, will replace with real db user check
        if (!username.equals("admin") || !password.equals("admin123")) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(username);
        return Map.of("token", token);
    }
}
