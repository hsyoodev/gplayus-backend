package com.gplayus.backend.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberExceptionCode implements GlobalExceptionCode {
    MEMBER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "MEM-001", "사용자 접근 권한 없음"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEM-002", "사용자 정보 없음");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
