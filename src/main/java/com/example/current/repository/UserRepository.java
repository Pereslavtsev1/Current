package com.example.current.repository;

import com.example.current.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<LocalUser, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<LocalUser> findByUsername(String username);

    Optional<LocalUser> findByEmail(String email);
}
