package com.auth.userAuthentication.data.dto.request;

/**
 * @author Ernest Emmanuel Utibe
 **/

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String email;
    private String password;
}
