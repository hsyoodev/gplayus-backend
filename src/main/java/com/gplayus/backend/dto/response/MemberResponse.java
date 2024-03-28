package com.gplayus.backend.dto.response;

import com.gplayus.backend.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String role;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole().name()
        );
    }
}
