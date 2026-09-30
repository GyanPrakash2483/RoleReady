package com.roleready.analysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.roleready.ai.AiService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
  private final AiService ai;
  private final Map<String,String> answers=new ConcurrentHashMap<>();
  public QuestionService(AiService ai){this.ai=ai;}

  public Map<String,Object> generate(JsonNode analysis){
    JsonNode node=ai.generateQuestions(analysis);
    List<ClarificationQuestion> out=new ArrayList<>();
    if(node.isArray()) for(JsonNode n:node) out.add(new ClarificationQuestion(
      UUID.randomUUID().toString(),n.path("question").asText(),n.path("reason").asText(),n.path("relatedCategory").asText()));
    return Map.of("questions",out);
  }

  public Map<String,Object> answer(String id,String answer){
    if(answer==null||answer.isBlank()) throw new IllegalArgumentException("Answer must not be empty");
    answers.put(id,answer);
    return Map.of("id",id,"confirmed",true);
  }

  public Map<String,String> confirmedAnswers(){return Map.copyOf(answers);}
}
