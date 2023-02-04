package com.example.test_project.Configs.JWT;

import com.example.test_project.Entites.TokensEntity;
import com.example.test_project.Repository.TokensRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${test.app.jwtSecret}")
    private String JWT_SECRET;

    @Value("${test.app.token.jwtExpirationMs}")
    private long JWT_TOKEN_EXPIRATION;

    @Value("${test.app.refreshToken.jwtExpirationMs}")
    private long JWT_REFRESH_TOKEN_EXPIRATION;

    @Autowired
    TokensRepository tokensRepository;

    /**
     * Generate token
     *
     * @param userId ID of user
     * @return token
     */
    public String generateToken(int userId) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + JWT_TOKEN_EXPIRATION);
        // Generate token from ID of user.
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    /**
     * Generate refreshToken
     *
     * @param userId ID of user
     * @return refreshToken
     */
    public String generateRefreshToken(int userId) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + JWT_REFRESH_TOKEN_EXPIRATION);
        // Generate refresh token from ID of user.
        var refreshToken = Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        // Initialization TokensEntity save
        var tokensEntity = TokensEntity.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .expiresIn(String.valueOf(expiryDate.getTime()))
                .build();

        tokensRepository.save(tokensEntity);

        return refreshToken;
    }

    /**
     * Validation token
     *
     * @param authToken token/refreshToken
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    /**
     * Get userId from token
     *
     * @param token token/refreshToken
     * @return userId
     */
    public int getUserIdFromJWT(String token) {
        var claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }

    /**
     * Get token from request
     *
     * @param request HttpServletRequest
     * @return token/null
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        // Check header has authorization
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }
}
