package com.roleready.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final OAuth2LoginSuccessHandler oauth2SuccessHandler;

  public SecurityConfig(JwtAuthFilter jwtAuthFilter, OAuth2LoginSuccessHandler oauth2SuccessHandler) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.oauth2SuccessHandler = oauth2SuccessHandler;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // Public: health, docs, auth, guest analysis (rate-limited by guest-use counter)
            .requestMatchers("/actuator/health", "/api/health", "/v3/api-docs/**", "/swagger-ui/**", "/oauth2/**", "/login/**").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/analysis", "/api/resume/upload").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(o -> o.successHandler(oauth2SuccessHandler))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
