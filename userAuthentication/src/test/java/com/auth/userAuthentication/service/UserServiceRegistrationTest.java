package com.auth.userAuthentication.service;

import com.auth.userAuthentication.data.repository.UserRepository;
import com.auth.userAuthentication.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceRegistrationTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testRegisterUser_SuccessfulRegistration() {
        // Test successful user registration
        String username = "john_doe";
        String email = "john.doe@example.com";
        String password = "testPassword";
        User user = new User(username, email, password);

        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.findByEmail(email)).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(username, email, password);

        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        assertEquals(email, registeredUser.getEmail());
        // Additional assertions as needed
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUser_DuplicateUsername() {
        // Test registration with a duplicate username
        String username = "john_doe";
        String email = "john.doe@example.com";
        String password = "testPassword";
        User existingUser = new User(username, email, password);

        when(userRepository.findByUsername(username)).thenReturn(existingUser);

        userService.registerUser(username, email, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUser_DuplicateEmail() {
        // Test registration with a duplicate email
        String username = "john_doe";
        String email = "john.doe@example.com";
        String password = "testPassword";
        User existingUser = new User(username, email, password);

        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.findByEmail(email)).thenReturn(existingUser);

        userService.registerUser(username, email, password);
    }
}
