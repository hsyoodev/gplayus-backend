package com.gplayus.backend.jwt.exception;

import com.gplayus.backend.exception.GlobalException;
import com.gplayus.backend.exception.GlobalExceptionCode;

public class JwtException extends GlobalException {
    public JwtException(GlobalExceptionCode globalExceptionCode, Object... rejectedValues) {
        super(globalExceptionCode, rejectedValues);
    }
}
