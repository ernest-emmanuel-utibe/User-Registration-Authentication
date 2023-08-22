package com.auth.userAuthentication.data.repository;

import com.auth.userAuthentication.data.model.SecurityDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityDetailRepository extends JpaRepository<SecurityDetail, Long> {

}
