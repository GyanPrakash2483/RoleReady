package com.roleready.optimization;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OptimizationService {

  public Map<String, Object> optimize(Map<String, Object> body) {
    // TODO: call AiService.optimizeResume with user-confirmed answers only (FR-AI-030..033),
    // produce before/after diff list with per-change accept/reject ids (FR-OPT-020..023).
    return Map.of("status", "optimization-stub");
  }

  public Map<String, Object> reviewChange(String id, Map<String, Object> body) {
    // TODO: body.decision = accept|reject
    return Map.of("id", id, "decision", body.getOrDefault("decision", "pending"));
  }
}
