package com.gplayus.backend.controller;

import com.gplayus.backend.jwt.exception.JwtException;
import com.gplayus.backend.jwt.util.JwtUtil;
import com.gplayus.backend.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.gplayus.backend.enums.MaxAgeType.ONE_DAY;
import static com.gplayus.backend.jwt.enums.AuthorizationType.BEARER;
import static com.gplayus.backend.jwt.enums.TokenType.ACCESS_TOKEN;
import static com.gplayus.backend.jwt.enums.TokenType.REFRESH_TOKEN;
import static com.gplayus.backend.jwt.exception.JwtExceptionCode.JWT_REFRESH_TOKEN_EXPIRED;
import static com.gplayus.backend.jwt.exception.JwtExceptionCode.JWT_REFRESH_TOKEN_INVALID;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Value("${spring.backend.login-url}")
    private String BACKEND_LOGIN_URL;

    @Value("${spring.jwt.access-token-expiration-time}")
    private Long JWT_ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.refresh-token-expiration-time}")
    private Long JWT_REFRESH_TOKEN_EXPIRATION_TIME;


    @GetMapping("/google")
    public void redirectGoogleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(BACKEND_LOGIN_URL);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> createAccessToken(HttpServletRequest request) {
        String refreshToken = cookieUtil.getRefreshToken(request);

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new JwtException(JWT_REFRESH_TOKEN_EXPIRED, refreshToken);
        } catch (MalformedJwtException e) {
            throw new JwtException(JWT_REFRESH_TOKEN_INVALID, refreshToken);
        }

        String type = jwtUtil.getType(refreshToken);

        if (!type.equals(REFRESH_TOKEN.getType())) {
            throw new JwtException(JWT_REFRESH_TOKEN_INVALID, refreshToken);
        }

        Long id = jwtUtil.getId(refreshToken);
        String email = jwtUtil.getEmail(refreshToken);
        String name = jwtUtil.getName(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        String newAccessToken = jwtUtil.createToken(
                ACCESS_TOKEN.getType(),
                id,
                email,
                name,
                role,
                JWT_ACCESS_TOKEN_EXPIRATION_TIME
        );

        String newRefreshToken = jwtUtil.createToken(
                REFRESH_TOKEN.getType(),
                id,
                email,
                name,
                role,
                JWT_REFRESH_TOKEN_EXPIRATION_TIME
        );

        ResponseCookie responseCookie = ResponseCookie.from(REFRESH_TOKEN.getType(), newRefreshToken)
                .httpOnly(true)
                .maxAge(ONE_DAY.getSeconds())
                .path("/")
                .secure(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .header(HttpHeaders.AUTHORIZATION, BEARER.getType() + newAccessToken)
                .build();
    }
}
