package com.gplayus.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "from")
public class GlobalExceptionResponse {
    private final String code;
}
