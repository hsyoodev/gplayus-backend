package com.gplayus.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("accessToken", MaxAgeType.ONE_DAY.getSeconds() * 7),
    REFRESH_TOKEN("refreshToken", MaxAgeType.ONE_DAY.getSeconds() * 1000);

    private final String type;
    private final Long expiredMs;
}
