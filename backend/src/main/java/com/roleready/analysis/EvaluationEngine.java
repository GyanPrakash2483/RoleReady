package com.roleready.analysis;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class EvaluationEngine {
  public Map<String,Integer> evaluate(Map<String,Object> resume,Map<String,Object> jd){
    String r=flatten(resume), j=flatten(jd);
    Map<String,Integer> scores=new LinkedHashMap<>();
    scores.put("requiredSkills", overlap(jd.get("requiredSkills"),r));
    scores.put("relevantExperience", overlap(jd.get("experience"),r));
    scores.put("responsibilityAlignment", overlap(jd.get("responsibilities"),r));
    scores.put("preferredSkills", overlap(jd.get("preferredSkills"),r));
    scores.put("keywordCoverage", overlap(jd.get("keywords"),r));
    scores.put("projectRelevance", overlap(jd.get("projects"),r));
    scores.put("achievementsImpact", overlap(jd.get("achievements"),r));
    scores.put("seniorityAlignment", overlap(jd.get("seniority"),r));
    scores.put("educationAlignment", overlap(jd.get("education"),r));
    scores.put("atsCompatibility", ats(r));
    scores.put("clarityReadability", clarity(r));
    scores.put("overallQuality", quality(r));
    return scores;
  }

  private int overlap(Object requirements,String resume){
    if(requirements==null) return 0;
    String[] terms=flatten(requirements).split("\\s+");
    if(terms.length==0)return 0;
    long hits=Arrays.stream(terms).filter(t->t.length()>2&&resume.contains(t)).count();
    return (int)Math.min(100,Math.round(100.0*hits/terms.length));
  }
  private int ats(String r){return r.isBlank()?0:Math.min(100,70+(r.split("\\s+").length>100?20:10));}
  private int clarity(String r){return r.isBlank()?0:(r.length()>200?85:60);}
  private int quality(String r){return r.isBlank()?0:(r.length()>500?85:65);}
  private String flatten(Object o){
    if(o==null)return "";
    if(o instanceof Map<?,?> m)return m.values().stream().map(this::flatten).reduce("",(a,b)->a+" "+b).toLowerCase(Locale.ROOT);
    if(o instanceof Collection<?> c)return c.stream().map(this::flatten).reduce("",(a,b)->a+" "+b).toLowerCase(Locale.ROOT);
    return String.valueOf(o).toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9+#.]+"," ");
  }
}
