package com.gplayus.backend.jwt.exception;


import com.gplayus.backend.exception.GlobalExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtExceptionCode implements GlobalExceptionCode {
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT-001", "액세스 토큰 만료"),
    JWT_ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "JWT-002", "액세스 토큰 유효하지 않음"),
    JWT_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT-003", "리프레시 토큰 만료"),
    JWT_REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "JWT-004", "리프레시 토큰 유효하지 않음"),
    JWT_SECRET_KEY_INVALID(HttpStatus.UNAUTHORIZED, "JWT-005", "비밀키 유효하지 않음");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
