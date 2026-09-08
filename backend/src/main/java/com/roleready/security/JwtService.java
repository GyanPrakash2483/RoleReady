package com.roleready.security;

import com.roleready.config.AppProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final AppProperties props;

  public JwtService(AppProperties props) {
    this.props = props;
  }

  private SecretKey key() {
    return Keys.hmacShaKeyFor(props.jwt().secret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String subject) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .subject(subject)
        .issuedAt(new Date(now))
        .expiration(new Date(now + props.jwt().expirationMs()))
        .signWith(key())
        .compact();
  }

  public String extractSubject(String token) {
    return Jwts.parser().verifyWith(key()).build()
        .parseSignedClaims(token).getPayload().getSubject();
  }
}
