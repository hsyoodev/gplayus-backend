package com.gplayus.backend.oauth2.service;

import com.gplayus.backend.domain.Member;
import com.gplayus.backend.dto.request.MemberRequest;
import com.gplayus.backend.dto.response.MemberResponse;
import com.gplayus.backend.oauth2.dto.GoogleOAuth2User;
import com.gplayus.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        MemberRequest memberRequest = MemberRequest.of(oAuth2User);

        Member savedMember = memberRepository.save(memberRequest.toEntity());

        return GoogleOAuth2User.of(MemberResponse.from(savedMember));
    }
}
