package com.auth.userAuthentication.data.dto.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String newEmail;
    private String newPassword;
}
