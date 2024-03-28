package com.gplayus.backend.config;

import com.gplayus.backend.oauth2.dto.GoogleOAuth2User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(GoogleOAuth2User.class::cast)
                .map(GoogleOAuth2User::getName);
    }
}
