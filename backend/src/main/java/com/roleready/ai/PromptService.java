package com.roleready.ai;

import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Prompts are application components (§28), not inline strings.
 * Each logical task gets its own template file under ai/prompts/.
 */
@Service
public class PromptService {

  public String render(String name, Map<String, Object> vars) {
    // TODO: load ai/prompts/<name>.md from classpath and interpolate {{vars}}.
    // Stub keeps the seam so prompt iteration doesn't touch business logic.
    return "[prompt:" + name + "] " + vars;
  }
}
