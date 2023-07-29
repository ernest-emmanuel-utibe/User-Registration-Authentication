package com.auth.userAuthentication.service;

import com.auth.userAuthentication.data.model.User;

public interface UserService {
    User registerUser(String username, String email, String password);
    User login(String usernameOrEmail, String password);

    User updateUser(Long userId, String newEmail, String newPassword);

    void deleteUser(Long userId);

    void resetPasswordByEmail(String email);
}
