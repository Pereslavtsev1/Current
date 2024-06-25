package com.example.current.repository;

import com.example.current.model.VerificationJWTEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationJWTEmailTokenRepository extends JpaRepository<VerificationJWTEmailToken, Long> {
    Optional<VerificationJWTEmailToken> findByToken(String token);

    void deleteByUserId(Long id);
}
