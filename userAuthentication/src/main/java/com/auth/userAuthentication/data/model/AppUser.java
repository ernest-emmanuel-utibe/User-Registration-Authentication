package com.auth.userAuthentication.data.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    private String email;
    private String password;
    private List<Role> roles;
    private boolean isEnabled;
}
