package com.auth.userAuthentication.service;

import org.junit.Test;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class UserServiceLoginTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLogin_SuccessfulLogin() {
        // Test successful user login
        String usernameOrEmail = "john_doe";
        String password = "testPassword";
        String hashedPassword = userService.hashPassword(password);

        User user = new User(usernameOrEmail, "john.doe@example.com", hashedPassword);

        when(userRepository.findByUsername(usernameOrEmail)).thenReturn(user);
        when(userRepository.findByEmail(usernameOrEmail)).thenReturn(null);

        User loggedInUser = userService.loginUser(usernameOrEmail, password);

        assertNotNull(loggedInUser);
        assertEquals(usernameOrEmail, loggedInUser.getUsername());
        // Additional assertions as needed
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLogin_InvalidPassword() {
        // Test login with an invalid password
        String usernameOrEmail = "john_doe";
        String password = "testPassword";
        String hashedPassword = userService.hashPassword("wrongPassword");

        User user = new User(usernameOrEmail, "john.doe@example.com", hashedPassword);

        when(userRepository.findByUsername(usernameOrEmail)).thenReturn(user);

        userService.loginUser(usernameOrEmail, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLogin_UserNotFound() {
        // Test login with a non-existing username/email
        String usernameOrEmail = "non_existing_user";
        String password = "testPassword";

        when(userRepository.findByUsername(usernameOrEmail)).thenReturn(null);
        when(userRepository.findByEmail(usernameOrEmail)).thenReturn(null);

        userService.loginUser(usernameOrEmail, password);
    }
}