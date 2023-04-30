package com.example.demo.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminprofile")
public class AdminController {
    
    @Value("${admin.username}")
    private String username;
    
    @Value("${admin.email}")
    private String email;
    
    @GetMapping
    public ResponseEntity<Object> getAdminProfile() {
        return ResponseEntity.ok()
                .body(String.format("{\"username\": \"%s\", \"email\": \"%s\"}", username, email));
    }
}

