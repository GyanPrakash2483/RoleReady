package com.roleready.ai;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
  public String render(String name, Map<String,Object> vars) {
    try {
      var resource=new ClassPathResource("ai/prompts/"+name+".md");
      String template=new String(resource.getInputStream().readAllBytes(),StandardCharsets.UTF_8);
      String result=template;
      for(var e:vars.entrySet()) result=result.replace("{{"+e.getKey()+"}}",String.valueOf(e.getValue()));
      return result;
    } catch(IOException e) { throw new IllegalStateException("AI prompt is unavailable: "+name,e); }
  }
}
