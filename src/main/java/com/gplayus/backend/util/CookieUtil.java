package com.gplayus.backend.util;

import com.gplayus.backend.exception.CookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import static com.gplayus.backend.exception.CookieExceptionCode.COOKIE_NOT_FOUND;
import static com.gplayus.backend.jwt.enums.TokenType.REFRESH_TOKEN;

@Component
public class CookieUtil {
    public String getRefreshToken(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new CookieException(COOKIE_NOT_FOUND, cookies);
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN.getType())) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        return refreshToken;
    }
}
