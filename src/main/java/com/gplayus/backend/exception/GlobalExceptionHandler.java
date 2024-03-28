package com.gplayus.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalExceptionResponse> handleGlobalExceptionResponse(GlobalException globalException) {
        GlobalExceptionCode globalExceptionCode = globalException.getGlobalExceptionCode();
        Object[] rejectedValues = globalException.getRejectedValues();

        log.warn("{}, rejectedValue: {}", globalExceptionCode.getMessage(), rejectedValues, globalException);

        return ResponseEntity.status(globalExceptionCode.getStatus())
                .body(GlobalExceptionResponse.from(globalExceptionCode.getCode()));
    }
}
