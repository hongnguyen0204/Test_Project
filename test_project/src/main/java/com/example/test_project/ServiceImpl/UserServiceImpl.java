package com.example.test_project.ServiceImpl;

import com.example.test_project.Configs.JWT.JwtUtils;
import com.example.test_project.Configs.UserConfig.UserDetailsCustom;
import com.example.test_project.Constant.Errors;
import com.example.test_project.Entites.UserEntity;
import com.example.test_project.Models.Common.UserInformationModel;
import com.example.test_project.Models.RequestModels.RefreshTokenRequestModel;
import com.example.test_project.Models.RequestModels.SigningRequestModel;
import com.example.test_project.Models.RequestModels.SignupRequestModel;
import com.example.test_project.Models.ResponseModel.RefreshTokenResponseModel;
import com.example.test_project.Models.ResponseModel.SigningResponseModel;
import com.example.test_project.Models.ResponseModel.SignupResponseModel;
import com.example.test_project.Repository.TokensRepository;
import com.example.test_project.Repository.UserRepository;
import com.example.test_project.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokensRepository tokensRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    /**
     * Sign in
     *
     * @param signingRequestModel SigningRequestModel
     * @return SigningResponseModel
     */
    @Override
    @Transactional
    public ResponseEntity<?> signing(SigningRequestModel signingRequestModel) {
        logger.info("[UserServiceImpl - signing] start.");
        // Authenticate from email and password.
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signingRequestModel.getEmail(),
                        signingRequestModel.getPassword()
                )
        );

        // Set authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token and refreshToken from user detail
        var userDetail = (UserDetailsCustom) authentication.getPrincipal();
        var userId = userDetail.getUser().getId();
        var token = jwtUtils.generateToken(userId);
        var refreshToken = jwtUtils.generateRefreshToken(userId);

        // Get information user from email
        var userEntity = userRepository.findByEmail(signingRequestModel.getEmail());
        var userInformation = new UserInformationModel(userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail());
        var response = SigningResponseModel.builder()
                .userInformationModel(userInformation)
                .token(token)
                .refreshToken(refreshToken)
                .build();

        logger.info("[UserServiceImpl - signing] end.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Sign up
     *
     * @param signingRequestModel SignupRequestModel
     * @return SignupResponseModel
     */
    @Override
    @Transactional
    public ResponseEntity<?> signup(SignupRequestModel signingRequestModel) {
        logger.info("[UserServiceImpl - signup] start.");
        // Check email existed or not
        var checkEmailExists = userRepository.existsByEmail(signingRequestModel.getEmail());
        if (checkEmailExists) {
            logger.error("[UserServiceImpl - signup]: {}", Errors.EMAIL_ALREADY_EXISTS);
            return new ResponseEntity<>(Errors.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }

        var user = new UserEntity(signingRequestModel.getFirstName(),
                signingRequestModel.getLastName(),
                signingRequestModel.getEmail(),
                encoder.encode(signingRequestModel.getPassword()));

        // Save new user
        var userEntity = userRepository.save(user);
        var responseModel = new SignupResponseModel(userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail()
        );
        logger.info("[UserServiceImpl - signup] end.");
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    /**
     * Sign Out
     */
    @Override
    @Transactional
    public void signOut(HttpServletRequest request) throws Exception {
        logger.info("[UserServiceImpl - signOut] start.");
        var token = jwtUtils.getJwtFromRequest(request);
        if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {
            // Get userId from token
            int userId = jwtUtils.getUserIdFromJWT(token);
            // Delete all refreshToken of userId
            tokensRepository.deleteAllByUserId(userId);
        } else {
            logger.error("[UserServiceImpl - signOut]: {}", Errors.UNAUTHORIZED);
            throw new Exception(Errors.UNAUTHORIZED);
        }
        logger.info("[UserServiceImpl - signOut] end.");
    }

    /**
     * Refresh token
     *
     * @param requestModel RefreshTokenRequestModel
     * @return RefreshTokenResponseModel
     */
    @Override
    @Transactional
    public ResponseEntity<?> refreshToken(RefreshTokenRequestModel requestModel) {
        logger.info("[UserServiceImpl - refreshToken] start.");
        var token = requestModel.getRefreshToken();
        // Check refreshToken is exists
        var check = tokensRepository.existsByRefreshToken(token);
        if (!check) {
            logger.error("[UserServiceImpl - refreshToken]: {}", Errors.TOKEN_DOES_NOT_EXISTS);
            return new ResponseEntity<>(Errors.TOKEN_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND);
        }

        // Check valid expires date of token
        var tokensEntity = tokensRepository.findFirstByRefreshToken(token);
        var dateNow = new Date();
        var dateRequestToken = new Date(Long.parseLong(tokensEntity.getExpiresIn()));
        // If date now after expires date of token, token has expired
        if (dateNow.after(dateRequestToken)) {
            logger.error("[UserServiceImpl - refreshToken]: {}", Errors.TOKEN_HAS_EXPIRED);
            return new ResponseEntity<>(Errors.TOKEN_HAS_EXPIRED, HttpStatus.BAD_REQUEST);
        }

        // Get userId from token
        int userId = jwtUtils.getUserIdFromJWT(token);
        // Generate new token
        var newToken = jwtUtils.generateToken(userId);
        // Generate new refresh token
        var newRefreshToken = jwtUtils.generateRefreshToken(userId);
        var response = RefreshTokenResponseModel.builder()
                .token(newToken)
                .refreshToken(newRefreshToken)
                .build();
        logger.info("[UserServiceImpl - refreshToken] end.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}