package com.example.test_project.Service;

import com.example.test_project.Models.RequestModels.RefreshTokenRequestModel;
import com.example.test_project.Models.RequestModels.SigningRequestModel;
import com.example.test_project.Models.RequestModels.SignupRequestModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signing(SigningRequestModel signingRequestModel);
    ResponseEntity<?> signup(SignupRequestModel signingRequestModel);
    void signOut(HttpServletRequest request) throws Exception;
    ResponseEntity<?> refreshToken(RefreshTokenRequestModel requestModel);
}
