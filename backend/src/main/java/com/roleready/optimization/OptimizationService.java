package com.roleready.optimization;

import com.roleready.ai.AiService;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class OptimizationService {
  private final AiService ai;
  private final Map<String,Map<String,Object>> changes=new HashMap<>();
  public OptimizationService(AiService ai){this.ai=ai;}

  public Map<String,Object> optimize(Map<String,Object> body){
    @SuppressWarnings("unchecked") Map<String,Object> resume=(Map<String,Object>)body.getOrDefault("resume",Map.of());
    @SuppressWarnings("unchecked") Map<String,Object> jd=(Map<String,Object>)body.getOrDefault("jd",Map.of());
    @SuppressWarnings("unchecked") Map<String,Object> answers=(Map<String,Object>)body.getOrDefault("answers",Map.of());
    var node=ai.optimizeResume(resume,jd,answers);
    List<Map<String,Object>> result=new ArrayList<>();
    if(node.isObject() && node.has("changes") && node.get("changes").isArray()){
      for(var n:node.get("changes")){
        String id=UUID.randomUUID().toString();
        Map<String,Object> change=new LinkedHashMap<>();
        change.put("id",id); change.put("section",n.path("section").asText());
        change.put("before",n.path("before").asText()); change.put("after",n.path("after").asText());
        change.put("provenance",n.path("provenance").asText("existing_evidence"));
        change.put("rationale",n.path("rationale").asText());
        change.put("decision","pending");
        changes.put(id,change); result.add(change);
      }
    }
    return Map.of("changes",result);
  }

  public Map<String,Object> finalResume(Map<String,Object> resume){
    Map<String,Object> result=new LinkedHashMap<>(resume);
    for(Map<String,Object> change:changes.values()){
      if("accept".equals(change.get("decision"))){
        String section=String.valueOf(change.get("section"));
        result.put(section,change.get("after"));
      }
    }
    return result;
  }

  public Map<String,Object> reviewChange(String id,Map<String,Object> body){
    Map<String,Object> change=changes.get(id);
    if(change==null) throw new IllegalArgumentException("Optimization change not found");
    String decision=String.valueOf(body.getOrDefault("decision","pending"));
    if(!Set.of("accept","reject","pending").contains(decision)) throw new IllegalArgumentException("Decision must be accept or reject");
    change.put("decision",decision);
    return change;
  }
}
