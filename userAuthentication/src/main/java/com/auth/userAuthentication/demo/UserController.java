package com.auth.userAuthentication.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @GetMapping
    public ResponseEntity<String> seyHelloUser() {
        return ResponseEntity.ok("Congratulations. You just got authenticated");
    }
}
