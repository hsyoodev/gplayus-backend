package com.gplayus.backend.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TesterExceptionCode implements GlobalExceptionCode {
    TESTER_NOT_FOUND(HttpStatus.NOT_FOUND, "TEST-001", "테스터 정보 없음");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
