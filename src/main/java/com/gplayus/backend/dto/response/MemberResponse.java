package com.gplayus.backend.dto.response;

import lombok.Getter;

@Getter
public class MemberResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final String role;

    private MemberResponse(Long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public static MemberResponse of(Long id, String email, String name, String role) {
        return new MemberResponse(id, email, name, role);
    }
}
