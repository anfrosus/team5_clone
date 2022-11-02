package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String email);
    void deleteByEmail(String email);
}