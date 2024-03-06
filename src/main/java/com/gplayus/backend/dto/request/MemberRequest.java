package com.gplayus.backend.dto.request;

import lombok.Getter;

@Getter
public class MemberRequest {

    private final Long id;
    private final String email;
    private final String name;
    private final String role;

    private MemberRequest(Long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public static MemberRequest of(Long id, String email, String name, String role) {
        return new MemberRequest(id, email, name, role);
    }
}
