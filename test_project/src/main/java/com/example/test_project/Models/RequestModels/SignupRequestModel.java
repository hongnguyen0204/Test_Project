package com.example.test_project.Models.RequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Getter
public class SignupRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("email")
    @NotBlank
    @Email(message = "Invalid format email.")
    private String email;

    @JsonProperty("password")
    @Size(min = 8, max = 20, message = "Password must be between 8-20 characters.")
    private String password;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
}
