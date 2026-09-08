package com.roleready.auth;

import com.roleready.auth.dto.AuthResponse;
import com.roleready.auth.dto.LoginRequest;
import com.roleready.auth.dto.RegisterRequest;
import com.roleready.security.JwtService;
import com.roleready.user.User;
import com.roleready.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository users;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(UserRepository users, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.users = users;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public AuthResponse register(RegisterRequest req) {
    if (users.findByEmail(req.email()).isPresent()) {
      throw new IllegalArgumentException("Email is already registered");
    }
    User user = new User();
    user.setEmail(req.email());
    user.setPasswordHash(passwordEncoder.encode(req.password()));
    users.save(user);
    // TODO: send verification email via MAIL_* settings (FR-AUTH-002)
    return new AuthResponse(jwtService.generateToken(user.getId()), user.getEmail());
  }

  public AuthResponse login(LoginRequest req) {
    User user = users.findByEmail(req.email())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    if (user.getPasswordHash() == null || !passwordEncoder.matches(req.password(), user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid email or password");
    }
    return new AuthResponse(jwtService.generateToken(user.getId()), user.getEmail());
  }

  // TODO: verifyEmail, forgotPassword, resetPassword, changePassword, googleOAuth (FR-AUTH-002/004/005/006)
}
