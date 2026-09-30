package com.roleready.analysis;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
  private final ScoringService scoring;
  private final EvaluationEngine evaluator;
  private final RequirementMatcher matcher;

  public AnalysisService(ScoringService scoring,EvaluationEngine evaluator,RequirementMatcher matcher){
    this.scoring=scoring;this.evaluator=evaluator;this.matcher=matcher;
  }

  @SuppressWarnings("unchecked")
  public Map<String,Object> analyze(Map<String,Object> body){
    Map<String,Object> resume=(Map<String,Object>)body.getOrDefault("resume",Map.of());
    Map<String,Object> jd=(Map<String,Object>)body.getOrDefault("jd",Map.of());
    String resumeText=String.valueOf(body.getOrDefault("resumeText",resume));
    Map<String,Integer> scores=evaluator.evaluate(resume,jd);

    List<Requirement> requirements=new ArrayList<>();
    addRequirements(requirements,jd.get("requiredSkills"),"requiredSkills","mandatory");
    addRequirements(requirements,jd.get("preferredSkills"),"preferredSkills","preferred");
    addRequirements(requirements,jd.get("responsibilities"),"responsibilityAlignment","mandatory");
    List<ResumeEvidence> evidence=matcher.match(requirements,resumeText);
    int penalty=(int)evidence.stream().filter(e->e.classification().equals("missing"))
        .mapToInt(e->e.criticality().equals("mandatory")?10:3).sum();
    penalty=Math.min(30,penalty);
    int score=scoring.compute(scores,penalty);
    Map<String,Object> explanations=new LinkedHashMap<>();
    for(var e:scores.entrySet()) explanations.put(e.getKey(),Map.of("score",e.getValue(),"weighted",scoring.weightedScores(scores).get(e.getKey())));
    return Map.of("roleReadiness",score,"categoryScores",scores,"explanations",explanations,
        "evidence",evidence,"mandatoryPenalty",penalty);
  }

  private void addRequirements(List<Requirement> out,Object value,String category,String criticality){
    if(value instanceof Collection<?> c) for(Object item:c){
      if(item instanceof Map<?,?> m){
        String name=String.valueOf(m.containsKey("name")?m.get("name"):"");
        String crit=String.valueOf(m.containsKey("criticality")?m.get("criticality"):criticality);
        if(!name.isBlank()) out.add(new Requirement(name,category,crit));
      } else if(!String.valueOf(item).isBlank()) out.add(new Requirement(String.valueOf(item),category,criticality));
    }
  }
}
