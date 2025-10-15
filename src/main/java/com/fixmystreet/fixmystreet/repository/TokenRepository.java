package com.fixmystreet.fixmystreet.repository;

import com.fixmystreet.fixmystreet.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findAllByUserIdAndExpiredFalseAndRevokedFalse(Long id);
}
