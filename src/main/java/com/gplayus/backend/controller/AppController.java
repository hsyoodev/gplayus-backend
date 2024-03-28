package com.gplayus.backend.controller;

import com.gplayus.backend.dto.request.AppRequest;
import com.gplayus.backend.jwt.exception.JwtException;
import com.gplayus.backend.jwt.util.JwtUtil;
import com.gplayus.backend.service.AppService;
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
import org.springframework.web.bind.annotation.*;

import static com.gplayus.backend.jwt.enums.TokenType.REFRESH_TOKEN;
import static com.gplayus.backend.jwt.exception.JwtExceptionCode.JWT_REFRESH_TOKEN_EXPIRED;
import static com.gplayus.backend.jwt.exception.JwtExceptionCode.JWT_REFRESH_TOKEN_INVALID;

@RequiredArgsConstructor
@RequestMapping("/apps")
@RestController
public class AppController {
    private final AppService appService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @GetMapping
    public ResponseEntity<?> findApps(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(appService.findApps(pageable));
    }

    @GetMapping("/{appId}")
    public ResponseEntity<?> findApp(@PathVariable Long appId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(appService.findApp(appId));
    }

    @PostMapping
    public ResponseEntity<?> createApp(HttpServletRequest request, @RequestBody AppRequest appRequest) {
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
                .body(appService.createApp(memberId, appRequest));
    }

    @PatchMapping("/{appId}")
    public ResponseEntity<?> updateApp(@PathVariable Long appId, @RequestBody AppRequest appRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(appService.updateApp(appId, appRequest));
    }

    @DeleteMapping("/{appId}")
    public ResponseEntity<?> deleteApp(@PathVariable Long appId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(appService.deleteApp(appId));
    }

    @PostMapping("/{appId}/members")
    public ResponseEntity<?> applyTester(HttpServletRequest request, @PathVariable Long appId) {
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
                .body(appService.applyTester(memberId, appId));
    }

    @PatchMapping("/{appId}/testers/{testerId}")
    public ResponseEntity<?> createTester(@PathVariable Long testerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(appService.createTester(testerId));
    }
}
