package com.auth.userAuthentication.data.dto.request;

/**
 * @author Ernest Emmanuel Utibe
 **/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;

    @Email(message = "Please provide a valid email address")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$", message = "Enter valid password")
    private String password;
}
