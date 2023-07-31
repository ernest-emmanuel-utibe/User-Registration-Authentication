package com.auth.userAuthentication.data.dto.request;

/**
 * @author Ernest Emmanuel Utibe
 **/

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
}
