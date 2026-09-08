package com.roleready.auth;

import com.roleready.auth.dto.AuthResponse;
import com.roleready.auth.dto.LoginRequest;
import com.roleready.auth.dto.RegisterRequest;
import com.roleready.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req) {
    return ResponseEntity.ok(ApiResponse.ok(authService.register(req)));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
    return ResponseEntity.ok(ApiResponse.ok(authService.login(req)));
  }

  // TODO: POST /google, /verify-email, /forgot-password, /reset-password, /change-password, DELETE /account
  // See docs/specifications.md §36 for the full logical API surface.
}
