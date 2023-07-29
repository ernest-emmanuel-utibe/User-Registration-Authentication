package com.auth.userAuthentication.data.repository;

import com.auth.userAuthentication.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

//    Optional<User> findByEmail(String email);
}
