package com.gplayus.backend.exception;

public class TesterException extends GlobalException {

    public TesterException(GlobalExceptionCode globalExceptionCode, Object... rejectedValues) {
        super(globalExceptionCode, rejectedValues);
    }
}
