package com.gplayus.backend.config;

import com.gplayus.backend.enums.MaxAgeType;
import com.gplayus.backend.exception.CustomAccessDeniedHandler;
import com.gplayus.backend.jwt.JwtExceptionFilter;
import com.gplayus.backend.jwt.JwtFilter;
import com.gplayus.backend.oauth2.CustomOAuth2UserService;
import com.gplayus.backend.oauth2.CustomOAuth2UserSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2UserSuccessHandler customOAuth2UserSuccessHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Value("${spring.frontend.base-url}")
    private String FRONTEND_BASE_URL;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfigurer -> csrfConfigurer.disable());

        http
                .cors(corsConfiguration -> corsConfiguration.configurationSource(
                        new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(
                                    HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();

                                configuration.setAllowedOrigins(
                                        Collections.singletonList(FRONTEND_BASE_URL));
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setMaxAge(MaxAgeType.ONE_HOUR.getSeconds());
                                configuration.setExposedHeaders(
                                        Collections.singletonList(HttpHeaders.SET_COOKIE));
                                configuration.setExposedHeaders(
                                        Collections.singletonList(HttpHeaders.AUTHORIZATION));

                                return configuration;
                            }
                        }));

        http.authorizeHttpRequests(
                authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer
                        .anyRequest().authenticated()
        );

        http
                .formLogin(formLoginConfigurer -> formLoginConfigurer.disable());

        http
                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable());

        http.
                oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        .loginPage(FRONTEND_BASE_URL)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(customOAuth2UserSuccessHandler)
                );

        http
                .addFilterAfter(jwtExceptionFilter, OAuth2LoginAuthenticationFilter.class)
                .addFilterAfter(jwtFilter, JwtExceptionFilter.class);

        http
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .accessDeniedHandler(customAccessDeniedHandler)
                );

        http
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
