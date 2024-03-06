package com.gplayus.backend.oauth2;

import com.gplayus.backend.dto.response.MemberResponse;
import com.gplayus.backend.enums.MaxAgeType;
import com.gplayus.backend.enums.TokenType;
import com.gplayus.backend.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class CustomOAuth2UserSuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final Environment environment;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        MemberResponse memberResponse = customOAuth2User.getMemberResponse();

        Long id = memberResponse.getId();
        String email = memberResponse.getEmail();
        String name = memberResponse.getName();
        String role = memberResponse.getRole();

        String accessToken = jwtUtil.createToken(
                TokenType.ACCESS_TOKEN.getType(),
                id,
                email,
                name,
                role,
                TokenType.ACCESS_TOKEN.getExpiredMs()
        );
        String refreshToken = jwtUtil.createToken(
                TokenType.REFRESH_TOKEN.getType(),
                id,
                email,
                name,
                role,
                TokenType.REFRESH_TOKEN.getExpiredMs()
        );

        ResponseCookie responseCookie = ResponseCookie
                .from(TokenType.REFRESH_TOKEN.getType(), refreshToken)
                .httpOnly(true)
                .maxAge(MaxAgeType.ONE_DAY.getSeconds())
                .path("/")
                .secure(true)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        String redirectUri = UriComponentsBuilder.fromUriString(
                        environment.getProperty("spring.frontend.redirect-uri"))
                .queryParam(TokenType.ACCESS_TOKEN.getType(), accessToken)
                .build()
                .toString();
        response.sendRedirect(redirectUri);
    }
}
