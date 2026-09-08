package com.roleready.user;

import com.roleready.common.ApiResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<Map<String, Object>>> me(Authentication auth) {
    // TODO: return account info; wire change-password / delete-account (FR-AUTH-006/007)
    return ResponseEntity.ok(ApiResponse.ok(Map.of("userId", String.valueOf(auth.getName()))));
  }
}
