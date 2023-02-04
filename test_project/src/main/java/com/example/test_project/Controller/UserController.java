package com.example.test_project.Controller;

import com.example.test_project.Constant.Errors;
import com.example.test_project.Constant.URL;
import com.example.test_project.Models.RequestModels.RefreshTokenRequestModel;
import com.example.test_project.Models.RequestModels.SigningRequestModel;
import com.example.test_project.Models.RequestModels.SignupRequestModel;
import com.example.test_project.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URL.API)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    /**
     * User sign up API
     *
     * @param requestModel SignupRequestModel
     * @return ResponseEntity<SignupResponseModel>
     */
    @PostMapping(URL.SIGN_UP)
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestModel requestModel) {
        logger.info("[UserController - signup] start.");
        try {
            return userService.signup(requestModel);
        } catch (Exception e) {
            logger.error("[UserController - signup]: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("[UserController - signup] end.");
        }
    }

    /**
     * User sign in API
     *
     * @param requestModel SigningRequestModel
     * @return ResponseEntity<?>
     */
    @PostMapping(URL.SIGN_IN)
    public ResponseEntity<?> signing(@Valid @RequestBody SigningRequestModel requestModel) {
        logger.info("[UserController - signing] start.");
        try {
            return userService.signing(requestModel);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(Errors.WRONG_EMAIL_OR_PASSWORD);
        } catch (Exception e) {
            logger.error("[UserController - signing]: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("[UserController - signing] end.");
        }
    }

    /**
     * Sign out API
     *
     * @param request HttpServletRequest
     * @return ResponseEntity<?>
     */
    @PostMapping(URL.SIGN_OUT)
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        logger.info("[UserController - signOut] start.");
        try {
            userService.signOut(request);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("[UserController - signOut]: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("[UserController - signOut] end.");
        }
    }

    /**
     * Refresh token API
     *
     * @param requestModel RefreshTokenRequestModel
     * @return ResponseEntity<?>
     */
    @PostMapping(URL.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestModel requestModel) {
        logger.info("[UserController - refreshToken] start.");
        try {
            return userService.refreshToken(requestModel);
        } catch (Exception e) {
            logger.error("[UserController - refreshToken]: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("[UserController - refreshToken] end.");
        }
    }
}
