package com.roleready.analysis;

import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Deterministic scoring per spec §14. Weights sum to 100%.
 * Missing mandatory requirements penalize significantly more than missing optional ones.
 */
@Service
public class ScoringService {

  public static final Map<String, Double> WEIGHTS = Map.ofEntries(
      Map.entry("requiredSkills", 0.20),
      Map.entry("relevantExperience", 0.15),
      Map.entry("responsibilityAlignment", 0.10),
      Map.entry("preferredSkills", 0.07),
      Map.entry("keywordCoverage", 0.07),
      Map.entry("projectRelevance", 0.07),
      Map.entry("achievementsImpact", 0.07),
      Map.entry("seniorityAlignment", 0.06),
      Map.entry("educationAlignment", 0.05),
      Map.entry("atsCompatibility", 0.05),
      Map.entry("clarityReadability", 0.05),
      Map.entry("overallQuality", 0.06));

  public int compute(Map<String, Integer> categoryScores, int mandatoryMissingPenalty) {
    double total = 0;
    for (var e : WEIGHTS.entrySet()) {
      total += e.getValue() * categoryScores.getOrDefault(e.getKey(), 0);
    }
    return Math.max(0, Math.min(100, (int) Math.round(total) - mandatoryMissingPenalty));
  }

  public int computeDefault() {
    return 0; // stub until AI validation lands
  }
}
