package com.roleready.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
    String frontendUrl,
    String corsAllowedOrigins,
    Jwt jwt,
    Guest guest,
    Session session,
    Gemini gemini) {
  public record Jwt(String secret, long expirationMs) {}
  public record Guest(int maxUses) {}
  public record Session(int ttlMinutes) {}
  public record Gemini(String apiKey, String model, long timeoutMs, String baseUrl) {}
}
