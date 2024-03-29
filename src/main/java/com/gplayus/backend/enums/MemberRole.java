package com.gplayus.backend.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberRole {
    ROLE_USER("USER");

    private final String role;
}
