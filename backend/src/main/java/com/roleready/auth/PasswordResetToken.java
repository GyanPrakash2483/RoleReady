package com.roleready.auth;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
  @Id
  @Column(columnDefinition = "CHAR(36)")
  private String id = UUID.randomUUID().toString();

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private com.roleready.user.User user;

  @Column(name = "token_hash", nullable = false)
  private String tokenHash;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Column(nullable = false)
  private boolean used;

  public String getId(){return id;}
  public com.roleready.user.User getUser(){return user;}
  public void setUser(com.roleready.user.User user){this.user=user;}
  public String getTokenHash(){return tokenHash;}
  public void setTokenHash(String tokenHash){this.tokenHash=tokenHash;}
  public Instant getExpiresAt(){return expiresAt;}
  public void setExpiresAt(Instant expiresAt){this.expiresAt=expiresAt;}
  public boolean isUsed(){return used;}
  public void setUsed(boolean used){this.used=used;}
}
