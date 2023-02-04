package com.example.test_project.Models.RequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.io.Serial;
import java.io.Serializable;

@Getter
public class RefreshTokenRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("refreshToken")
    private String refreshToken;
}
