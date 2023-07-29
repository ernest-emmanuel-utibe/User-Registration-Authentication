package com.auth.userAuthentication.service;

import com.auth.userAuthentication.data.model.User;
import com.auth.userAuthentication.data.repository.UserRepository;
import com.auth.userAuthentication.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceAccountManagementTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testUpdateUser_SuccessfulUpdate() {
        // Test successful user account update
        Long userId = 1L;
        String newEmail = "new_email@example.com";
        String newPassword = "newPassword";
        String hashedNewPassword = userService.hashPassword(newPassword);

        // Create an existing user with old information
        User existingUser = new User("john_doe", "john.doe@example.com", "oldPassword");
        existingUser.setId(userId);

        // Mock the behavior of userRepository.findById(userId)
        when(userRepository.findById(Math.toIntExact(userId))).thenReturn(Optional.of(existingUser));

        // Mock the behavior of userRepository.save(user)
        // In this example, we assume that the userRepository returns the same user after saving it
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the updateUser method in userService
        User updatedUser = userService.updateUser(userId, newEmail, newPassword);

        // Assert that the updatedUser is not null
        assertNotNull(updatedUser);

        // Assert that the userId remains unchanged
        assertEquals(userId, updatedUser.getId());

        // Assert that the email has been updated to newEmail
        assertEquals(newEmail, updatedUser.getEmail());

        // Assert that the password has been updated and hashed
        assertEquals(hashedNewPassword, updatedUser.getPassword());

        // Additional assertions to check if the user information has been properly updated
        // For example, if you have an 'updatedAt' field in the User entity, you can check:
        assertNotNull(updatedUser.getUpdatedAt());

        @Test
        public void testUpdateUser_UserNotFound() {
            // Test update with a non-existing user
            Long userId = 1L;
            String newEmail = "new_email@example.com";
            String newPassword = "newPassword";

            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            userService.updateUser(userId, newEmail, newPassword);
        }
    }
}
