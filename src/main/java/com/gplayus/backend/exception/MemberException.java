package com.gplayus.backend.exception;

public class MemberException extends GlobalException {

    public MemberException(GlobalExceptionCode globalExceptionCode, Object... rejectedValues) {
        super(globalExceptionCode, rejectedValues);
    }
}
