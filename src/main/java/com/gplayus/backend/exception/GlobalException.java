package com.gplayus.backend.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final GlobalExceptionCode globalExceptionCode;
    private final Object[] rejectedValues;

    protected GlobalException(GlobalExceptionCode globalExceptionCode, Object... rejectedValues) {
        super(globalExceptionCode.getMessage());
        this.globalExceptionCode = globalExceptionCode;
        this.rejectedValues = rejectedValues;
    }
}
