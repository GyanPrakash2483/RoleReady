package com.roleready.analysis;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class ScoringService {
  public static final Map<String,Double> WEIGHTS=Map.ofEntries(
    Map.entry("requiredSkills",.20),Map.entry("relevantExperience",.15),Map.entry("responsibilityAlignment",.10),
    Map.entry("preferredSkills",.07),Map.entry("keywordCoverage",.07),Map.entry("projectRelevance",.07),
    Map.entry("achievementsImpact",.07),Map.entry("seniorityAlignment",.06),Map.entry("educationAlignment",.05),
    Map.entry("atsCompatibility",.05),Map.entry("clarityReadability",.05),Map.entry("overallQuality",.06));

  public int compute(Map<String,Integer> categoryScores,int mandatoryMissingPenalty){
    double total=0; for(var e:WEIGHTS.entrySet()) total+=e.getValue()*categoryScores.getOrDefault(e.getKey(),0);
    return Math.max(0,Math.min(100,(int)Math.round(total)-mandatoryMissingPenalty));
  }
  public Map<String,Double> weightedScores(Map<String,Integer> scores){
    Map<String,Double> result=new LinkedHashMap<>();
    for(var e:WEIGHTS.entrySet()) result.put(e.getKey(),e.getValue()*scores.getOrDefault(e.getKey(),0));
    return result;
  }
}
