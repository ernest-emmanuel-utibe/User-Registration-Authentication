package com.auth.userAuthentication.security.service;

import com.auth.userAuthentication.data.model.AppUser;
import com.auth.userAuthentication.data.model.Role;
import com.auth.userAuthentication.data.repository.AppUserRepository;
import com.auth.userAuthentication.security.user.SecureUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByEmail (username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }
        return SecureUser.builder()
                .user(user)
                .roles (List.of (Role.CUSTOMER, Role.ADMIN))
                .build();
    }
}
