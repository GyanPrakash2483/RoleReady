package com.roleready.resume;

import com.roleready.common.ApiResponse;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

  private final ResumeService resumeService;

  public ResumeController(ResumeService resumeService) {
    this.resumeService = resumeService;
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ApiResponse<Map<String, Object>>> upload(@RequestPart("file") MultipartFile file) {
    return ResponseEntity.ok(ApiResponse.ok(resumeService.parseUpload(file)));
  }

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Map<String, Object>>> create(@RequestBody Map<String, Object> resume) {
    return ResponseEntity.ok(ApiResponse.ok(resumeService.create(resume)));
  }

  @GetMapping("/current")
  public ResponseEntity<ApiResponse<Map<String, Object>>> current() {
    return ResponseEntity.ok(ApiResponse.ok(resumeService.current()));
  }

  @PutMapping("/current")
  public ResponseEntity<ApiResponse<Map<String, Object>>> update(@RequestBody Map<String, Object> resume) {
    return ResponseEntity.ok(ApiResponse.ok(resumeService.update(resume)));
  }
}
