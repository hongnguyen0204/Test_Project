package com.example.test_project.Entites;

import com.example.test_project.Utils.AnnotationCustom.CreatedAt;
import com.example.test_project.Utils.AnnotationCustom.UpdatedAt;
import com.example.test_project.Utils.EntityListener.EntityListenerCustom;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Tokens")
@Table(name = "Tokens")
@EntityListeners(EntityListenerCustom.class)
public class TokensEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private String refreshToken;

    private String expiresIn;

    @UpdatedAt
    private LocalDateTime updatedAt;

    @CreatedAt
    private LocalDateTime createdAt;
}
