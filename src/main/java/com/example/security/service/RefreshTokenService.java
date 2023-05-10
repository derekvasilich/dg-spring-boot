package com.example.security.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.RefreshToken;
import com.example.repositories.RefreshTokenRepository;
import com.example.repositories.UserRepository;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${app.jwtRefreshExpirationMs}")
    long jwtRefreshExpirationMs;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken token = new RefreshToken(
            userRepository.findById(userId).get(),
            Instant.now().plusMillis(jwtRefreshExpirationMs),
            UUID.randomUUID().toString()
        );
        token = refreshTokenRepository.save(token);
        return token;
    }

    public RefreshToken verifyExpiry(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please login again.");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
