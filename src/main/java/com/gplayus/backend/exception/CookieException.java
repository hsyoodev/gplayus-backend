package com.gplayus.backend.exception;

public class CookieException extends GlobalException {

    public CookieException(GlobalExceptionCode globalExceptionCode, Object... rejectedValues) {
        super(globalExceptionCode, rejectedValues);
    }
}
