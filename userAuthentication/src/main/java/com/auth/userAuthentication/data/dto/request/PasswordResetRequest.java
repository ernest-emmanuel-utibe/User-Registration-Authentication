package com.auth.userAuthentication.data.dto.request;

/**
 * @author Ernest Emmanuel Utibe
 **/

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PasswordResetRequest {

    @Email(message = "Please provide a valid email address")
    private String email;
}
