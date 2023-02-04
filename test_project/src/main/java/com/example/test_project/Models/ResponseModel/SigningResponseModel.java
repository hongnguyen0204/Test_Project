package com.example.test_project.Models.ResponseModel;

import com.example.test_project.Models.Common.UserInformationModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class SigningResponseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("user")
    private UserInformationModel userInformationModel;

    /**
     * Jwt token
     */
    @JsonProperty("token")
    private String token;

    /**
     * Jwt refresh token
     */
    @JsonProperty("refreshToken")
    private String refreshToken;
}
