package com.gplayus.backend.controller;

import com.gplayus.backend.jwt.exception.JwtException;
import com.gplayus.backend.jwt.util.JwtUtil;
import com.gplayus.backend.service.MemberService;
import com.gplayus.backend.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gplayus.backend.jwt.enums.TokenType.REFRESH_TOKEN;
import static com.gplayus.backend.jwt.exception.JwtExceptionCode.JWT_REFRESH_TOKEN_EXPIRED;
import static com.gplayus.backend.jwt.exception.JwtExceptionCode.JWT_REFRESH_TOKEN_INVALID;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @GetMapping("/apps")
    public ResponseEntity<?> findAppsByMemberId(HttpServletRequest request, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        String refreshToken = cookieUtil.getRefreshToken(request);

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new JwtException(JWT_REFRESH_TOKEN_EXPIRED, refreshToken);
        } catch (MalformedJwtException e) {
            throw new JwtException(JWT_REFRESH_TOKEN_INVALID, refreshToken);
        }

        String type = jwtUtil.getType(refreshToken);

        if (!type.equals(REFRESH_TOKEN.getType())) {
            throw new JwtException(JWT_REFRESH_TOKEN_INVALID, refreshToken);
        }

        Long memberId = jwtUtil.getId(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.findAppsByMemberId(memberId, pageable));
    }

    @GetMapping("/testers/apps")
    public ResponseEntity<?> findAppsByTesterId(HttpServletRequest request, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        String refreshToken = cookieUtil.getRefreshToken(request);

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new JwtException(JWT_REFRESH_TOKEN_EXPIRED, refreshToken);
        } catch (MalformedJwtException e) {
            throw new JwtException(JWT_REFRESH_TOKEN_INVALID, refreshToken);
        }

        String type = jwtUtil.getType(refreshToken);

        if (!type.equals(REFRESH_TOKEN.getType())) {
            throw new JwtException(JWT_REFRESH_TOKEN_INVALID, refreshToken);
        }

        Long testerId = jwtUtil.getId(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.findAppsByTesterId(testerId, pageable));
    }
}
