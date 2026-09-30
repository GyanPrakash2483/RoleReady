package com.roleready.ai;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class StructuredAiResponseParserTest {
  private final StructuredAiResponseParser parser=new StructuredAiResponseParser();
  @Test void extractsGeminiCandidateJson(){
    var node=parser.extractJson("{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\"{\\\"ok\\\":true}\"}]}}]}");
    assertThat(node.path("ok").asBoolean()).isTrue();
  }
}
