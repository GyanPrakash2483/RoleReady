package com.roleready.user;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(columnDefinition = "CHAR(36)")
  private String id = UUID.randomUUID().toString();

  @Column(nullable = false, unique = true, length = 320)
  private String email;

  @Column(name = "password_hash", length = 255)
  private String passwordHash; // null for pure Google-OAuth accounts

  @Column(name = "google_sub", unique = true, length = 255)
  private String googleSub;

  @Column(name = "email_verified", nullable = false)
  private boolean emailVerified = false;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  // Getters/setters (kept explicit to avoid Lombok requirement)
  public String getId() { return id; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public String getGoogleSub() { return googleSub; }
  public void setGoogleSub(String googleSub) { this.googleSub = googleSub; }
  public boolean isEmailVerified() { return emailVerified; }
  public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
  public Instant getCreatedAt() { return createdAt; }
}
