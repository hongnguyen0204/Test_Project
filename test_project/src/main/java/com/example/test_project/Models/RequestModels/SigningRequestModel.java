package com.example.test_project.Models.RequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigningRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("email")
    @Email(message = "Invalid format email.")
    private String email;

    @JsonProperty("password")
    @Size(min = 8, max = 20, message = "Password must be between 8-20 characters.")
    private String password;
}
