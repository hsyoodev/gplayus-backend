package com.gplayus.backend.config;

import com.gplayus.backend.oauth2.CustomOAuth2User;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        return () -> Optional.of(customOAuth2User.getName());
    }
}
