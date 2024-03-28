package com.gplayus.backend.oauth2.dto;

import com.gplayus.backend.dto.request.MemberRequest;
import com.gplayus.backend.dto.response.MemberResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleOAuth2User implements OAuth2User {
    private final MemberResponse memberResponse;
    private final MemberRequest memberRequest;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> {
            if (memberResponse == null) {
                return memberRequest.getRole();
            }

            return memberResponse.getRole();
        });

        return collection;
    }

    @Override
    public String getName() {
        if (memberResponse == null) {
            return memberRequest.getName();
        }

        return memberResponse.getName();
    }

    public static GoogleOAuth2User of(MemberResponse memberResponse) {
        return new GoogleOAuth2User(memberResponse, null);
    }

    public static GoogleOAuth2User of(MemberRequest memberRequest) {
        return new GoogleOAuth2User(null, memberRequest);
    }
}
