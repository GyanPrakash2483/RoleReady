package com.roleready.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.roleready.auth.dto.RegisterRequest;
import com.roleready.security.JwtService;
import com.roleready.user.User;
import com.roleready.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock UserRepository users;
  @Mock JwtService jwtService;
  @Mock VerificationTokenRepository verificationTokens;
  @Mock PasswordResetTokenRepository resetTokens;
  @Mock org.springframework.mail.javamail.JavaMailSender mailSender;

  @Test
  void registrationStoresOnlyEncodedPassword() {
    AuthService service = new AuthService(users, new BCryptPasswordEncoder(), jwtService,
        verificationTokens, resetTokens, mailSender, "http://localhost:4200", "");
    when(users.findByEmail("test@example.com")).thenReturn(java.util.Optional.empty());
    when(users.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
    when(jwtService.generateToken(any())).thenReturn("token");

    service.register(new RegisterRequest("test@example.com", "password123"));

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(users).save(captor.capture());
    assertThat(captor.getValue().getPasswordHash()).isNotEqualTo("password123");
    assertThat(captor.getValue().getPasswordHash()).startsWith("$2a$");
  }
}
