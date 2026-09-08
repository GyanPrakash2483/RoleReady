package com.roleready.export;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

  public byte[] exportPdf(Map<String, Object> resume) {
    // TODO: generate professionally formatted PDF preserving template where possible (FR-OUT-001/002).
    throw new UnsupportedOperationException("PDF export not yet implemented");
  }

  public String exportMarkdown(Map<String, Object> resume) {
    // TODO: preserve original Markdown structure (FR-OUT-010/011).
    throw new UnsupportedOperationException("Markdown export not yet implemented");
  }

  public String exportLatex(Map<String, Object> resume) {
    // TODO: preserve original LaTeX template strictly (FR-OUT-020/021).
    throw new UnsupportedOperationException("LaTeX export not yet implemented");
  }
}
