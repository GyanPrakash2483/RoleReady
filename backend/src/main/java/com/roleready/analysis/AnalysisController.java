package com.roleready.analysis;

import com.roleready.common.ApiResponse;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

  private final AnalysisService analysisService;

  public AnalysisController(AnalysisService analysisService) {
    this.analysisService = analysisService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> analyze(@RequestBody Map<String, Object> body) {
    return ResponseEntity.ok(ApiResponse.ok(analysisService.analyze(body)));
  }

  @GetMapping("/current")
  public ResponseEntity<ApiResponse<Map<String, Object>>> current() {
    return ResponseEntity.ok(ApiResponse.ok(Map.of("status", "empty-stub")));
  }

  @PostMapping("/questions/answer")
  public ResponseEntity<ApiResponse<Map<String, Object>>> answer(@RequestBody Map<String, Object> body) {
    return ResponseEntity.ok(ApiResponse.ok(Map.of("status", "answers-stored-stub")));
  }
}
