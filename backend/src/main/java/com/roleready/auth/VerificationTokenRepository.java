package com.roleready.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
  Optional<VerificationToken> findByTokenHashAndUsedFalse(String tokenHash);
}
