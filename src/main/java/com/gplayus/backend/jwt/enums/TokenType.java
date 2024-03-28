package com.gplayus.backend.jwt.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TokenType {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");

    private final String type;
}
