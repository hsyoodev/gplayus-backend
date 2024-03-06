package com.gplayus.backend.oauth2;

import com.gplayus.backend.domain.Member;
import com.gplayus.backend.dto.response.MemberResponse;
import com.gplayus.backend.enums.MemberRole;
import com.gplayus.backend.repository.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();
        String name = attributes.get("name").toString();
        MemberRole role = MemberRole.ROLE_USER;

        Member member = Member.of(email, name, role, name, name);
        Member savedMember = memberRepository.save(member);
        Long savedId = savedMember.getId();
        String savedEmail = savedMember.getEmail();
        String savedName = savedMember.getName();
        MemberRole savedRole = savedMember.getRole();

        MemberResponse memberResponse = MemberResponse.of(savedId, savedEmail, savedName,
                savedRole.name());
        CustomOAuth2User customOAuth2User = CustomOAuth2User.of(memberResponse);

        return customOAuth2User;
    }
}
