package com.gplayus.backend.exception;

import org.springframework.http.HttpStatus;

public interface GlobalExceptionCode {
    HttpStatus getStatus();

    String getCode();

    String getMessage();
}
