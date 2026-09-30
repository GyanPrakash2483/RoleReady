package com.roleready.auth;

import com.roleready.auth.dto.*;
import com.roleready.security.JwtService;
import com.roleready.user.User;
import com.roleready.user.UserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private final UserRepository users;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final VerificationTokenRepository verificationTokens;
  private final PasswordResetTokenRepository resetTokens;
  private final JavaMailSender mailSender;
  private final String frontendUrl;
  private final String mailFrom;

  public AuthService(UserRepository users, PasswordEncoder passwordEncoder, JwtService jwtService,
      VerificationTokenRepository verificationTokens, PasswordResetTokenRepository resetTokens,
      JavaMailSender mailSender, @Value("${app.frontend-url:http://localhost:4200}") String frontendUrl,
      @Value("${spring.mail.username:}") String mailFrom) {
    this.users=users; this.passwordEncoder=passwordEncoder; this.jwtService=jwtService;
    this.verificationTokens=verificationTokens; this.resetTokens=resetTokens;
    this.mailSender=mailSender; this.frontendUrl=frontendUrl; this.mailFrom=mailFrom;
  }

  public AuthResponse register(RegisterRequest req) {
    if (users.findByEmail(req.email()).isPresent()) throw new IllegalArgumentException("Email is already registered");
    User user=new User(); user.setEmail(req.email()); user.setPasswordHash(passwordEncoder.encode(req.password()));
    users.save(user); sendVerification(user);
    return new AuthResponse(jwtService.generateToken(user.getId()), user.getEmail());
  }

  public AuthResponse login(LoginRequest req) {
    User user=users.findByEmail(req.email()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    if(user.getPasswordHash()==null || !passwordEncoder.matches(req.password(),user.getPasswordHash()))
      throw new IllegalArgumentException("Invalid email or password");
    return new AuthResponse(jwtService.generateToken(user.getId()),user.getEmail());
  }

  @Transactional
  public void verifyEmail(String rawToken) {
    VerificationToken token=verificationTokens.findByTokenHashAndUsedFalse(hash(rawToken))
        .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));
    if(token.getExpiresAt().isBefore(Instant.now())) throw new IllegalArgumentException("Verification token has expired");
    token.getUser().setEmailVerified(true);
    token.setUsed(true);
    verificationTokens.save(token);
  }

  public void forgotPassword(String email) {
    users.findByEmail(email).ifPresent(this::sendReset);
  }

  @Transactional
  public void resetPassword(ResetPasswordRequest req) {
    PasswordResetToken token=resetTokens.findByTokenHashAndUsedFalse(hash(req.token()))
        .orElseThrow(() -> new IllegalArgumentException("Invalid reset token"));
    if(token.getExpiresAt().isBefore(Instant.now())) throw new IllegalArgumentException("Reset token has expired");
    User user=token.getUser();
    user.setPasswordHash(passwordEncoder.encode(req.password()));
    token.setUsed(true);
    users.save(user); resetTokens.save(token);
  }

  public void changePassword(String userId, ChangePasswordRequest req) {
    User user=users.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    if(user.getPasswordHash()==null || !passwordEncoder.matches(req.currentPassword(),user.getPasswordHash()))
      throw new IllegalArgumentException("Current password is incorrect");
    user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
    users.save(user);
  }

  public void deleteAccount(String userId) {
    if(!users.existsById(userId)) throw new IllegalArgumentException("User not found");
    users.deleteById(userId);
  }

  private void sendVerification(User user) {
    String raw=UUID.randomUUID().toString()+UUID.randomUUID();
    VerificationToken token=new VerificationToken();
    token.setUser(user); token.setTokenHash(hash(raw)); token.setExpiresAt(Instant.now().plus(24,ChronoUnit.HOURS));
    verificationTokens.save(token);
    sendMail(user.getEmail(),"Verify your RoleReady account",
        frontendUrl+"/verify-email?token="+raw);
  }

  private void sendReset(User user) {
    String raw=UUID.randomUUID().toString()+UUID.randomUUID();
    PasswordResetToken token=new PasswordResetToken();
    token.setUser(user); token.setTokenHash(hash(raw)); token.setExpiresAt(Instant.now().plus(30,ChronoUnit.MINUTES));
    resetTokens.save(token);
    sendMail(user.getEmail(),"Reset your RoleReady password",
        frontendUrl+"/reset-password?token="+raw);
  }

  private void sendMail(String to,String subject,String body) {
    if(mailFrom.isBlank()) return;
    SimpleMailMessage msg=new SimpleMailMessage();
    msg.setFrom(mailFrom); msg.setTo(to); msg.setSubject(subject); msg.setText(body);
    mailSender.send(msg);
  }

  private static String hash(String value) {
    try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); }
    catch(Exception e){ throw new IllegalStateException("Unable to hash token",e); }
  }
}
