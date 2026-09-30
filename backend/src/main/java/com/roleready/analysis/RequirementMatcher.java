package com.roleready.analysis;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RequirementMatcher {
  public List<ResumeEvidence> match(List<Requirement> requirements,String resumeText){
    String resume=normalize(resumeText);
    List<ResumeEvidence> result=new ArrayList<>();
    for(Requirement req:requirements){
      String[] terms=normalize(req.name()).split("\\s+");
      long hits=Arrays.stream(terms).filter(t->t.length()>2 && resume.contains(t)).count();
      String classification=hits==terms.length?"strong":hits>0?"partial":"missing";
      String evidence=classification.equals("missing")?"No explicit resume evidence found.":"Matched "+hits+" of "+terms.length+" key terms.";
      result.add(new ResumeEvidence(req.name(),classification,evidence,req.criticality()));
    }
    return result;
  }

  private String normalize(String s){return s==null?"":s.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9+#.]+"," ").trim();}
}
