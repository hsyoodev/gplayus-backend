package com.gplayus.backend.exception;

public class AppException extends GlobalException {

    public AppException(GlobalExceptionCode globalExceptionCode, Object... rejectedValues) {
        super(globalExceptionCode, rejectedValues);
    }
}
