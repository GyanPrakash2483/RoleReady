package com.roleready.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class StructuredAiResponseParser {
  private final ObjectMapper mapper=new ObjectMapper();
  public JsonNode extractJson(String raw){
    try {
      JsonNode root=mapper.readTree(raw);
      JsonNode text=root.at("/candidates/0/content/parts/0/text");
      if(text.isTextual()) return mapper.readTree(text.asText().trim());
      if(root.isObject()) return root;
      throw new IllegalArgumentException("AI response did not contain JSON");
    } catch(Exception e) { throw new IllegalArgumentException("AI response was not valid structured JSON",e); }
  }
}
