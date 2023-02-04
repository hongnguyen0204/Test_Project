package com.example.test_project.Repository;

import com.example.test_project.Entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Find user by email
     *
     * @param email Email of user
     * @return UserEntity
     */
    Optional<UserEntity> findFirstByEmail(String email);

    /**
     * Find user by email
     *
     * @param email Email of user
     * @return UserEntity
     */
    UserEntity findByEmail(String email);

    /**
     * Check user existed with email
     *
     * @param email Email of user
     * @return boolean
     */
    boolean existsByEmail(String email);
}
