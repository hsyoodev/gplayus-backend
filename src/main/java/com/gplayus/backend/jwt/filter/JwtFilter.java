package com.gplayus.backend.jwt.filter;

import com.gplayus.backend.dto.request.MemberRequest;
import com.gplayus.backend.jwt.util.JwtUtil;
import com.gplayus.backend.oauth2.dto.GoogleOAuth2User;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.gplayus.backend.jwt.enums.AuthorizationType.BEARER;
import static com.gplayus.backend.jwt.enums.TokenType.ACCESS_TOKEN;
import static com.gplayus.backend.jwt.exception.JwtExceptionCode.JWT_ACCESS_TOKEN_INVALID;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith(BEARER.getType())) {
            filterChain.doFilter(request, response);

            return;
        }

        String accessToken = authorization.split(" ")[1];

        jwtUtil.isExpired(accessToken);

        String type = jwtUtil.getType(accessToken);

        if (!type.equals(ACCESS_TOKEN.getType())) {
            throw new MalformedJwtException(JWT_ACCESS_TOKEN_INVALID.getCode());
        }

        Long id = jwtUtil.getId(accessToken);
        String email = jwtUtil.getEmail(accessToken);
        String name = jwtUtil.getName(accessToken);
        String role = jwtUtil.getRole(accessToken);

        MemberRequest memberRequest = MemberRequest.of(id, email, name, role);
        GoogleOAuth2User googleOAuth2User = GoogleOAuth2User.of(memberRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                googleOAuth2User, null, googleOAuth2User.getAuthorities());
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
