package com.roleready.auth;

import com.roleready.auth.dto.*;
import com.roleready.common.ApiResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;
  public AuthController(AuthService authService){this.authService=authService;}

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req){
    return ResponseEntity.ok(ApiResponse.ok(authService.register(req)));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req){
    return ResponseEntity.ok(ApiResponse.ok(authService.login(req)));
  }

  @PostMapping("/verify-email")
  public ResponseEntity<ApiResponse<Void>> verifyEmail(@Valid @RequestBody VerifyEmailRequest req){
    authService.verifyEmail(req.token());
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req){
    authService.forgotPassword(req.email());
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest req){
    authService.resetPassword(req);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  @PostMapping("/change-password")
  public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest req, Principal principal){
    authService.changePassword(principal.getName(),req);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  @DeleteMapping("/account")
  public ResponseEntity<ApiResponse<Void>> deleteAccount(Principal principal){
    authService.deleteAccount(principal.getName());
    return ResponseEntity.ok(ApiResponse.ok(null));
  }
}
