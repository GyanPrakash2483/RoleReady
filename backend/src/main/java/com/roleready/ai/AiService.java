package com.roleready.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roleready.config.AppProperties;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Single gateway to Gemini. All business services go through here —
 * never call the Gemini API directly from controllers/services (§27).
 */
@Service
public class AiService {

  private final AppProperties props;
  private final RestClient gemini;
  private final PromptService prompts;
  private final StructuredAiResponseParser parser;
  private final ObjectMapper mapper = new ObjectMapper();

  public AiService(AppProperties props, RestClient gemini, PromptService prompts, StructuredAiResponseParser parser) {
    this.props = props;
    this.gemini = gemini;
    this.prompts = prompts;
    this.parser = parser;
  }

  public JsonNode analyzeResumeAgainstJd(Map<String, Object> resume, Map<String, Object> jd) {
    String prompt = prompts.render("evaluation", Map.of("resume", resume, "jd", jd));
    return callGeminiJson(prompt);
  }

  public JsonNode extractJobDescription(String text) {
    return callGeminiJson(prompts.render("jd-extraction", Map.of("text", text)));
  }

  public JsonNode generateQuestions(JsonNode analysis) {
    return callGeminiJson(prompts.render("question-generation", Map.of("analysis", analysis.toString())));
  }

  public JsonNode optimizeResume(Map<String, Object> resume, Map<String, Object> jd,
      Map<String, Object> answers) {
    return callGeminiJson(prompts.render("optimization",
        Map.of("resume", resume, "jd", jd, "answers", answers)));
  }

  private JsonNode callGeminiJson(String prompt) {
    if (props.gemini().apiKey() == null || props.gemini().apiKey().isBlank()) {
      throw new IllegalStateException("GEMINI_API_KEY is not configured");
    }
    try {
      Map<String, Object> body = Map.of(
          "contents", new Object[] {Map.of("parts", new Object[] {Map.of("text", prompt)})},
          "generationConfig", Map.of("responseMimeType", "application/json"));
      String raw = gemini.post()
          .uri(props.gemini().baseUrl() + "/v1beta/models/"
              + props.gemini().model() + ":generateContent?key=" + props.gemini().apiKey())
          .contentType(MediaType.APPLICATION_JSON)
          .body(body)
          .retrieve()
          .body(String.class);
      return parser.extractJson(raw);
    } catch (Exception e) {
      throw new IllegalStateException("AI request failed. Please try again.");
    }
  }
}
