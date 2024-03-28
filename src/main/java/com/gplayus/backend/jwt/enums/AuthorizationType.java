package com.gplayus.backend.jwt.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthorizationType {
    BEARER("Bearer ");

    private final String type;
}
