package com.gplayus.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gplayus.backend.exception.GlobalExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.gplayus.backend.exception.MemberExceptionCode.MEMBER_ACCESS_DENIED;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.getWriter()
                .write(objectMapper.writeValueAsString(GlobalExceptionResponse.from(MEMBER_ACCESS_DENIED.getCode())));
        response.setStatus(MEMBER_ACCESS_DENIED.getStatus().value());
    }
}
