package com.roleready.job;

import com.roleready.common.ApiResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-description")
public class JobDescriptionController {

  private final JobDescriptionService jobDescriptionService;

  public JobDescriptionController(JobDescriptionService jobDescriptionService) {
    this.jobDescriptionService = jobDescriptionService;
  }

  @PostMapping("/analyze")
  public ResponseEntity<ApiResponse<Map<String, Object>>> analyze(@RequestBody Map<String, String> body) {
    String text = body.getOrDefault("text", "");
    if (text.isBlank()) throw new IllegalArgumentException("Job description text must not be empty");
    return ResponseEntity.ok(ApiResponse.ok(jobDescriptionService.extract(text)));
  }
}
