package com.example.test_project.Models.ResponseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import java.io.Serial;
import java.io.Serializable;

@Builder
public class RefreshTokenResponseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("token")
    private String token;

    @JsonProperty("refreshToken")
    private String refreshToken;
}
