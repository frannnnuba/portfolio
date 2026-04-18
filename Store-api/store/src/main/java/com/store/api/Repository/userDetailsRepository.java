package com.store.api.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.store.api.Entity.Users;

public interface userDetailsRepository extends JpaRepository<Users,Long> {

    Optional<UserDetails> findByUsername(String username);

}
