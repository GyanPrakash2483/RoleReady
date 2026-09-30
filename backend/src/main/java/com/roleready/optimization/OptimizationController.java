package com.roleready.optimization;

import com.roleready.common.ApiResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/optimization")
public class OptimizationController {

  private final OptimizationService optimizationService;

  public OptimizationController(OptimizationService optimizationService) {
    this.optimizationService = optimizationService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> optimize(@RequestBody Map<String, Object> body) {
    return ResponseEntity.ok(ApiResponse.ok(optimizationService.optimize(body)));
  }

  @PostMapping("/final")
  public ResponseEntity<ApiResponse<Map<String,Object>>> finalResume(@RequestBody Map<String,Object> resume) {
    return ResponseEntity.ok(ApiResponse.ok(optimizationService.finalResume(resume)));
  }

  @PutMapping("/changes/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> reviewChange(
      @PathVariable String id, @RequestBody Map<String, Object> body) {
    return ResponseEntity.ok(ApiResponse.ok(optimizationService.reviewChange(id, body)));
  }
}
