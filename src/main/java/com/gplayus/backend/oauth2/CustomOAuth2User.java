package com.gplayus.backend.oauth2;

import com.gplayus.backend.dto.request.MemberRequest;
import com.gplayus.backend.dto.response.MemberResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final MemberResponse memberResponse;
    private final MemberRequest memberRequest;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                if (memberResponse == null) {
                    return memberRequest.getRole();
                }

                return memberResponse.getRole();
            }
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

    private CustomOAuth2User(MemberResponse memberResponse, MemberRequest memberRequest) {
        this.memberResponse = memberResponse;
        this.memberRequest = memberRequest;
    }

    public static CustomOAuth2User of(MemberResponse memberResponse) {
        return new CustomOAuth2User(memberResponse, null);
    }

    public static CustomOAuth2User of(MemberRequest memberRequest) {
        return new CustomOAuth2User(null, memberRequest);
    }
}
