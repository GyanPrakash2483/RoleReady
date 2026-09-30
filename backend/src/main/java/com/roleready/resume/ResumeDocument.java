package com.roleready.resume;

import java.util.Map;

public record ResumeDocument(
    String format,
    String originalFilename,
    String rawText,
    Map<String,String> sections
) {}
