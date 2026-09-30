package com.roleready.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roleready.ai.AiService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class JobDescriptionService {
  private final AiService aiService;
  private final ObjectMapper mapper=new ObjectMapper();

  public JobDescriptionService(AiService aiService){this.aiService=aiService;}

  public Map<String,Object> extract(String text){
    if(text==null || text.isBlank()) throw new IllegalArgumentException("Job description text must not be empty");
    if(text.length()>50000) throw new IllegalArgumentException("Job description is too long");
    var node=aiService.extractJobDescription(text);
    if(!node.isObject()) throw new IllegalStateException("AI returned an invalid job description");
    return mapper.convertValue(node,Map.class);
  }
}
