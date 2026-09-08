package com.roleready.export;

import com.roleready.common.ApiResponse;
import java.util.Map;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
public class ExportController {

  private final ExportService exportService;

  public ExportController(ExportService exportService) {
    this.exportService = exportService;
  }

  @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> pdf(@RequestBody Map<String, Object> resume) {
    byte[] pdf = exportService.exportPdf(resume);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            ContentDisposition.attachment().filename("roleready-resume.pdf").build().toString())
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }

  @PostMapping("/markdown")
  public ResponseEntity<ApiResponse<Map<String, Object>>> markdown(@RequestBody Map<String, Object> resume) {
    return ResponseEntity.ok(ApiResponse.ok(Map.of("markdown", exportService.exportMarkdown(resume))));
  }

  @PostMapping("/latex")
  public ResponseEntity<ApiResponse<Map<String, Object>>> latex(@RequestBody Map<String, Object> resume) {
    return ResponseEntity.ok(ApiResponse.ok(Map.of("latex", exportService.exportLatex(resume))));
  }
}
