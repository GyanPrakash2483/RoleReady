package com.roleready.session;

import java.util.Map;

public record RoleReadySession(
    Object resume,
    Object jobDescription,
    Object analysis,
    Object questions,
    Object answers,
    Object optimization
) {
  public static RoleReadySession empty() {
    return new RoleReadySession(null,null,null,null,null,null);
  }
  public Map<String,Object> asMap() {
    return Map.of(
      "resume", resume == null ? Map.of() : resume,
      "jobDescription", jobDescription == null ? Map.of() : jobDescription,
      "analysis", analysis == null ? Map.of() : analysis,
      "questions", questions == null ? Map.of() : questions,
      "answers", answers == null ? Map.of() : answers,
      "optimization", optimization == null ? Map.of() : optimization
    );
  }
}
