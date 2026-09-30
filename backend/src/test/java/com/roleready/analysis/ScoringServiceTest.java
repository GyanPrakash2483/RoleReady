package com.roleready.analysis;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ScoringServiceTest {
  private final ScoringService scoring=new ScoringService();

  @Test void weightsTotal100Percent(){
    assertThat(ScoringService.WEIGHTS.values().stream().mapToDouble(Double::doubleValue).sum()).isEqualTo(1.0);
  }

  @Test void missingMandatoryRequirementsReduceScore(){
    int full=scoring.compute(Map.of("requiredSkills",100),0);
    int penalized=scoring.compute(Map.of("requiredSkills",100),20);
    assertThat(penalized).isEqualTo(full-20);
  }

  @Test void scoreIsNormalized(){
    assertThat(scoring.compute(Map.of("requiredSkills",100),0)).isBetween(0,100);
    assertThat(scoring.compute(Map.of(),1000)).isZero();
  }
}
