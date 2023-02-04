package com.example.test_project.Utils.EntityListener;

import com.example.test_project.Utils.AnnotationCustom.CreatedAt;
import com.example.test_project.Utils.AnnotationCustom.UpdatedAt;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class EntityListenerCustom {
    @PrePersist
    private void setCreatedAt(Object obj)
            throws IllegalArgumentException, IllegalAccessException, SecurityException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CreatedAt.class)) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            }
            if (field.isAnnotationPresent(UpdatedAt.class)) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            }
        }
    }

    @PreUpdate
    private void setUpdatedAt(Object obj)
            throws IllegalArgumentException, IllegalAccessException, SecurityException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(UpdatedAt.class)) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            }
        }
    }
}
