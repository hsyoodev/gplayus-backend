package com.gplayus.backend.jwt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gplayus.backend.exception.GlobalExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.gplayus.backend.jwt.exception.JwtExceptionCode.*;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.getWriter()
                    .write(objectMapper.writeValueAsString(GlobalExceptionResponse.from(JWT_ACCESS_TOKEN_EXPIRED.getCode())));
            response.setStatus(JWT_ACCESS_TOKEN_EXPIRED.getStatus().value());
        } catch (MalformedJwtException e) {
            response.getWriter()
                    .write(objectMapper.writeValueAsString(GlobalExceptionResponse.from(JWT_ACCESS_TOKEN_INVALID.getCode())));
            response.setStatus(JWT_ACCESS_TOKEN_INVALID.getStatus().value());
        } catch (SignatureException e) {
            response.getWriter()
                    .write(objectMapper.writeValueAsString(GlobalExceptionResponse.from(JWT_SECRET_KEY_INVALID.getCode())));
            response.setStatus(JWT_SECRET_KEY_INVALID.getStatus().value());
        }
    }
}
