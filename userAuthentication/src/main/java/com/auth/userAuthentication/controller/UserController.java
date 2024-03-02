package com.auth.userAuthentication.controller;

/**
 * @author Ernest Emmanuel Utibe
 **/

import com.auth.userAuthentication.data.dto.request.LoginRequest;
import com.auth.userAuthentication.data.dto.request.PasswordResetRequest;
import com.auth.userAuthentication.data.dto.request.UpdateUserRequest;
import com.auth.userAuthentication.data.dto.request.UserRegistrationRequest;
import com.auth.userAuthentication.data.model.User;
import com.auth.userAuthentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            User newUser = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok("User registered successfully with ID: " + newUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request.getUsernameOrEmail(), request.getPassword());
            // Optionally, you can implement session management or generate a JWT token here.
            return ResponseEntity.ok("Login successful for user: " + user.getUsername());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserAccount(@PathVariable Long userId,
                                                    @RequestBody UpdateUserRequest request) {
        try {
            User updatedUser = userService.updateUser(userId, request.getNewEmail(), request.getNewPassword());
            return ResponseEntity.ok("Account information updated for user: " + updatedUser.getUsername());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User with ID " + userId + " has been deactivated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPasswordByEmail(@RequestBody PasswordResetRequest request) {
        try {
            userService.resetPasswordByEmail(request.getEmail());
            return ResponseEntity.ok("Password reset email sent successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
