package com.auth.userAuthentication.data.repository;

import com.auth.userAuthentication.data.model.AppUser;
import com.auth.userAuthentication.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}
