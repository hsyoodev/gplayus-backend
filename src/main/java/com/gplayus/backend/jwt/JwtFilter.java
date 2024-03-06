package com.gplayus.backend.jwt;

import com.gplayus.backend.dto.request.MemberRequest;
import com.gplayus.backend.enums.TokenType;
import com.gplayus.backend.oauth2.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final String AUTHORIZATION_TYPE = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith(AUTHORIZATION_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.split(" ")[1];

        if (jwtUtil.isExpired(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String type = jwtUtil.getType(accessToken);

        if (!type.equals(TokenType.ACCESS_TOKEN.getType())) {
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtUtil.getId(accessToken);
        String email = jwtUtil.getEmail(accessToken);
        String name = jwtUtil.getName(accessToken);
        String role = jwtUtil.getRole(accessToken);

        MemberRequest memberRequest = MemberRequest.of(id, email, name, role);
        CustomOAuth2User customOAuth2User = CustomOAuth2User.of(memberRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
