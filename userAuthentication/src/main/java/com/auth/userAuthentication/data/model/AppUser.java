package com.auth.userAuthentication.data.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    private String email;
    private String password;
    private String role;
    private boolean isEnabled;
}
