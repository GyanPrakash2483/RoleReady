package com.roleready.resume;

import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

  private static final Set<String> ALLOWED = Set.of(
      "application/pdf",
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      "text/plain", "text/markdown", "application/x-latex", "text/x-latex");

  public Map<String, Object> parseUpload(MultipartFile file) {
    if (file.isEmpty()) throw new IllegalArgumentException("Empty resume file");
    // TODO: validate extension + magic bytes (NFR-SEC-004/008), extract PDF/DOCX/TXT/MD/LaTeX,
    // detect sections (FR-RES-004), preserve MD/LaTeX structure (FR-RES-021/022).
    return Map.of("filename", file.getOriginalFilename(), "status", "parsed-stub");
  }

  public Map<String, Object> create(Map<String, Object> resume) {
    // TODO: structured creation with custom sections (FR-RES-010..015)
    return Map.of("status", "created-stub");
  }

  public Map<String, Object> current() {
    // TODO: read from session-scoped temporary storage (never permanent, FR-PRIV-001)
    return Map.of("status", "empty-stub");
  }

  public Map<String, Object> update(Map<String, Object> resume) {
    // TODO: persist to session only
    return Map.of("status", "updated-stub");
  }
}
