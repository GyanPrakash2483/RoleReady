package com.roleready.optimization;

import com.roleready.common.ApiResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {
  private final SuggestionService service;
  public SuggestionController(SuggestionService service){this.service=service;}
  @PostMapping public ResponseEntity<ApiResponse<Map<String,Object>>> generate(@RequestBody Map<String,Object> body){
    return ResponseEntity.ok(ApiResponse.ok(service.generate(body)));
  }
}
