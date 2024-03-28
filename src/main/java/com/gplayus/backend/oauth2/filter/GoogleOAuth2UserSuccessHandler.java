package com.gplayus.backend.oauth2.filter;

import com.gplayus.backend.dto.response.MemberResponse;
import com.gplayus.backend.jwt.util.JwtUtil;
import com.gplayus.backend.oauth2.dto.GoogleOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.gplayus.backend.enums.MaxAgeType.ONE_DAY;
import static com.gplayus.backend.jwt.enums.TokenType.ACCESS_TOKEN;
import static com.gplayus.backend.jwt.enums.TokenType.REFRESH_TOKEN;

@RequiredArgsConstructor
@Component
public class GoogleOAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Value("${spring.frontend.redirect-uri}")
    private String FRONTEND_REDIRECT_URI;

    @Value("${spring.jwt.access-token-expiration-time}")
    private Long JWT_ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.refresh-token-expiration-time}")
    private Long JWT_REFRESH_TOKEN_EXPIRATION_TIME;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        GoogleOAuth2User googleOAuth2User = (GoogleOAuth2User) authentication.getPrincipal();
        MemberResponse memberResponse = googleOAuth2User.getMemberResponse();

        Long id = memberResponse.getId();
        String email = memberResponse.getEmail();
        String name = memberResponse.getName();
        String role = memberResponse.getRole();

        String accessToken = jwtUtil.createToken(
                ACCESS_TOKEN.getType(),
                id,
                email,
                name,
                role,
                JWT_ACCESS_TOKEN_EXPIRATION_TIME
        );

        String refreshToken = jwtUtil.createToken(
                REFRESH_TOKEN.getType(),
                id,
                email,
                name,
                role,
                JWT_REFRESH_TOKEN_EXPIRATION_TIME
        );

        ResponseCookie responseCookie = ResponseCookie.from(REFRESH_TOKEN.getType(), refreshToken)
                .httpOnly(true)
                .maxAge(ONE_DAY.getSeconds())
                .path("/")
                .secure(true)
                .build();

        String redirectUri = UriComponentsBuilder.fromUriString(FRONTEND_REDIRECT_URI)
                .queryParam(ACCESS_TOKEN.getType(), accessToken)
                .build()
                .toString();

        response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        response.sendRedirect(redirectUri);
    }
}
