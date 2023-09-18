package com.auth.userAuthentication.service.impl;

/**
 * @author Ernest Emmanuel Utibe
 **/

import com.auth.userAuthentication.data.model.User;
import com.auth.userAuthentication.data.repository.UserRepository;
import com.auth.userAuthentication.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JavaMailSender mailSender;

    @Value("${app.mail.sender}")
    private String mailSenderAddress;

    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User registerUser(String username, String email, String password) {
        // Perform validation for unique username and email
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email address already exists.");
        }

        // Perform additional validation for username, email, and password format
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        // Validate email format using a simple regular expression
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        // Hash the user's password
        String hashedPassword = hashPassword(password);

        // Create the new user entity and save it to the database
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, String newEmail, String newPassword) {
        // Find the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Update the user's email if provided
        if (newEmail != null && !newEmail.isEmpty()) {
            user.setEmail(newEmail);
        }

        // Update the user's password if provided
        if (newPassword != null && !newPassword.isEmpty()) {
            String hashedPassword = hashPassword(newPassword);
            user.setPassword(hashedPassword);
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        // Find the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setActive(false);
        userRepository.save(user);
    }



    // Helper method to hash the password (using BCrypt in this example)
    public String hashPassword(String password) {
        passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    // Helper method to validate email format using a regular expression
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    @Override
    public User login(String usernameOrEmail, String password) {
        // Find the user by username or email
        User user = userRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        // Validate the provided password with the hashed password from the database
        passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        // User login is successful; no additional session management needed in this example.
        return user;
    }

    @Override
    public void resetPasswordByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with the provided email.");
        }

        // Generate a random temporary password
        String temporaryPassword = generateRandomPassword();

        // Hash the temporary password
        String hashedTemporaryPassword = hashPassword(temporaryPassword);

        // Save the hashed temporary password to the user entity in the database
        user.setPassword(hashedTemporaryPassword);
        userRepository.save(user);

        // Send the password reset email
        sendPasswordResetEmail(email, temporaryPassword);
    }

    private String generateRandomPassword() {
        // Generate a random alphanumeric password with a length of 10 characters
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private void sendPasswordResetEmail(String email, String temporaryPassword) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            // Set the email properties
            helper.setTo(email);
            helper.setSubject("Password Reset");

            // Compose the email body
            String emailBody = "Dear User,\n\n"
                    + "You have requested a password reset. Your temporary password is: " + temporaryPassword + "\n"
                    + "Please use this temporary password to log in and reset your password.\n\n"
                    + "Best regards,\nYour App Team";

            helper.setText(emailBody, false);

            // Set the sender of the email
            helper.setFrom(mailSenderAddress);

            // Send the email
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle email sending errors
            throw new RuntimeException("Failed to send password reset email.");
        }
    }
}
