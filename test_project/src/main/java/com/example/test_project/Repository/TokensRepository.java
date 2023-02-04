package com.example.test_project.Repository;

import com.example.test_project.Entites.TokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends JpaRepository<TokensEntity, Integer> {
    boolean existsByRefreshToken(String token);
    void deleteAllByUserId(int userId);
    TokensEntity findFirstByRefreshToken(String refreshToken);
}
