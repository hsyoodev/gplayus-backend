package com.gplayus.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gplayus.backend.enums.MaxAgeType;
import com.gplayus.backend.enums.MemberRole;
import com.gplayus.backend.filter.CustomAccessDeniedHandler;
import com.gplayus.backend.jwt.exception.JwtExceptionFilter;
import com.gplayus.backend.jwt.filter.JwtFilter;
import com.gplayus.backend.jwt.util.JwtUtil;
import com.gplayus.backend.oauth2.filter.GoogleOAuth2UserSuccessHandler;
import com.gplayus.backend.oauth2.service.GoogleOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final GoogleOAuth2UserService googleOAuth2UserService;
    private final GoogleOAuth2UserSuccessHandler googleOAuth2UserSuccessHandler;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Value("${spring.frontend.base-url}")
    private String FRONTEND_BASE_URL;

    @Value("${spring.frontend.login-url}")
    private String FRONTEND_LOGIN_URL;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable);

        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource -> {
                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(Collections.singletonList(FRONTEND_BASE_URL));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setMaxAge(MaxAgeType.ONE_HOUR.getSeconds());

                    configuration.setExposedHeaders(Collections.singletonList(HttpHeaders.AUTHORIZATION));

                    return configuration;
                }));

        http
                .authorizeHttpRequests(authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer
                        .requestMatchers(HttpMethod.GET, "/apps").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/apps").hasRole(MemberRole.ROLE_USER.getRole())
                        .anyRequest().authenticated()
                );

        http
                .formLogin(FormLoginConfigurer::disable);

        http
                .httpBasic(HttpBasicConfigurer::disable);

        http
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        .loginPage(FRONTEND_LOGIN_URL)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(googleOAuth2UserService)
                        )
                        .successHandler(googleOAuth2UserSuccessHandler)
                );


        http
                .addFilterAfter(new JwtExceptionFilter(objectMapper), OAuth2LoginAuthenticationFilter.class)
                .addFilterAfter(new JwtFilter(jwtUtil), JwtExceptionFilter.class);

        http
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
                );

        http
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
