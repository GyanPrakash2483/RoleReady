package com.roleready.resume;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class ResumeParserTest {
  private final ResumeParser parser=new ResumeParser();
  @Test void parsesMarkdownSections(){
    var file=new MockMultipartFile("file","resume.md","text/markdown","# John\n\n## Skills\nJava, Spring\n".getBytes());
    var doc=parser.parse(file);
    assertThat(doc.format()).isEqualTo("md");
    assertThat(doc.sections()).containsKey("skills");
  }
  @Test void rejectsUnsupportedFormat(){
    var file=new MockMultipartFile("file","resume.exe","application/octet-stream","bad".getBytes());
    assertThatThrownBy(()->parser.parse(file)).isInstanceOf(IllegalArgumentException.class);
  }
}
