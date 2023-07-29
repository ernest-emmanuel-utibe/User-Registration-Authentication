package com.auth.userAuthentication.data.dto.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
}
