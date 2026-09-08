package com.roleready.analysis;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

  private final ScoringService scoringService;

  public AnalysisService(ScoringService scoringService) {
    this.scoringService = scoringService;
  }

  public Map<String, Object> analyze(Map<String, Object> body) {
    // TODO: 1) call AiService.analyzeResumeAgainstJd 2) validate schema (FR-LLM-002)
    // 3) compute score in Java via ScoringService (spec §13: never blindly trust LLM score)
    int score = scoringService.computeDefault();
    return Map.of("roleReadiness", score, "status", "analysis-stub");
  }
}
