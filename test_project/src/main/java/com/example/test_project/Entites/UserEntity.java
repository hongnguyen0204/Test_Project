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
@Entity(name = "Users")
@Table(name = "Users")
@EntityListeners(EntityListenerCustom.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @UpdatedAt
    private LocalDateTime updatedAt;

    @CreatedAt
    private LocalDateTime createdAt;

    public UserEntity(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
