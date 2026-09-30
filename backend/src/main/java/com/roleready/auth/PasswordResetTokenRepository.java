package com.roleready.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
  Optional<PasswordResetToken> findByTokenHashAndUsedFalse(String tokenHash);
}
