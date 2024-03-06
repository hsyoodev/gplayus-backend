package com.gplayus.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaxAgeType {
    ONE_MINUTE(60L),
    ONE_HOUR(ONE_MINUTE.getSeconds() * 60L),
    ONE_DAY(ONE_HOUR.getSeconds() * 24L);

    private final Long seconds;
}
