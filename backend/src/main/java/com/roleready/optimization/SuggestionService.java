package com.roleready.optimization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roleready.ai.AiService;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {
  private final AiService ai; private final ObjectMapper mapper=new ObjectMapper();
  public SuggestionService(AiService ai){this.ai=ai;}
  public Map<String,Object> generate(Map<String,Object> body){
    var node=ai.generateSuggestions(body);
    if(!node.isArray()) throw new IllegalStateException("Invalid suggestion response");
    List<Suggestion> suggestions=new ArrayList<>();
    for(var n:node) suggestions.add(new Suggestion(UUID.randomUUID().toString(),
      text(n,"category"),text(n,"rationale"),text(n,"action"),text(n,"proposedContent")));
    return Map.of("suggestions",suggestions);
  }
  private String text(com.fasterxml.jackson.databind.JsonNode n,String k){return n.path(k).asText("");}
}
