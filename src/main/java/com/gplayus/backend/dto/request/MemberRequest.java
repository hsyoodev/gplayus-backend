package com.gplayus.backend.dto.request;

import com.gplayus.backend.domain.Member;
import com.gplayus.backend.enums.MemberRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {
    private Long id;
    private String email;
    private String name;
    private String role;

    public static MemberRequest of(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();
        String name = attributes.get("name").toString();
        String role = MemberRole.ROLE_USER.name();

        return new MemberRequest(null, email, name, role);
    }

    public static MemberRequest of(Long id, String email, String name, String role) {
        return new MemberRequest(id, email, name, role);
    }

    public Member toEntity() {
        return Member.of(email, name, MemberRole.valueOf(role), name, name);
    }
}
