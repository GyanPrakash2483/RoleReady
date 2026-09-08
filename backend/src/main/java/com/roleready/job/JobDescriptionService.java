package com.roleready.job;

import com.roleready.ai.AiService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class JobDescriptionService {

  private final AiService aiService;

  public JobDescriptionService(AiService aiService) {
    this.aiService = aiService;
  }

  public Map<String, Object> extract(String text) {
    // TODO: delegate to AiService JD-extraction prompt (FR-JD-002/003/004)
    return Map.of("status", "jd-extracted-stub", "chars", text.length());
  }
}
