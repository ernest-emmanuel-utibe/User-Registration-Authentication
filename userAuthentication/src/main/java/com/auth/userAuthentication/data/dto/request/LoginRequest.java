package com.auth.userAuthentication.data.dto.request;

/**
 * @author Ernest Emmanuel Utibe
 **/

import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
