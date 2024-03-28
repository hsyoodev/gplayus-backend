package com.gplayus.backend.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AppExceptionCode implements GlobalExceptionCode {
    APP_NOT_FOUND(HttpStatus.NOT_FOUND, "APP-001", "앱 정보 없음");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
