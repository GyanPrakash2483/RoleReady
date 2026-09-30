package com.roleready.analysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roleready.common.ApiResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
  private final QuestionService service;
  private final ObjectMapper mapper=new ObjectMapper();
  public QuestionController(QuestionService service){this.service=service;}

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String,Object>>> generate(@RequestBody Map<String,Object> body){
    return ResponseEntity.ok(ApiResponse.ok(service.generate(mapper.valueToTree(body.getOrDefault("analysis",Map.of())))));
  }

  @PostMapping("/{id}/answer")
  public ResponseEntity<ApiResponse<Map<String,Object>>> answer(@PathVariable String id,@RequestBody Map<String,String> body){
    return ResponseEntity.ok(ApiResponse.ok(service.answer(id,body.getOrDefault("answer",""))));
  }
}
