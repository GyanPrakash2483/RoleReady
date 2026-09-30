package com.roleready.export;

import java.io.*;
import java.util.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Service;

@Service
public class ExportService {
  public byte[] exportPdf(Map<String,Object> resume){
    try(var document=new PDDocument(); var out=new ByteArrayOutputStream()){
      PDPage page=new PDPage(PDRectangle.LETTER); document.addPage(page);
      try(var stream=new PDPageContentStream(document,page)){
        stream.beginText(); stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA),11); stream.setLeading(15);
        stream.newLineAtOffset(50,740);
        for(String line:exportMarkdown(resume).split("\\R")){
          stream.showText(line.replaceAll("[^\\x20-\\x7E]",""));
          stream.newLine();
        }
        stream.endText();
      }
      document.save(out); return out.toByteArray();
    }catch(IOException e){throw new IllegalStateException("Unable to generate PDF",e);}
  }

  public String exportMarkdown(Map<String,Object> resume){
    Object source=resume.get("sourceContent");
    if("md".equalsIgnoreCase(String.valueOf(resume.get("sourceFormat"))) && source!=null) return String.valueOf(source);
    StringBuilder md=new StringBuilder();
    Map<String,Object> personal=map(resume.get("personal"));
    if(personal.containsKey("name")) md.append("# ").append(personal.get("name")).append("\n\n");
    append(md,"email",personal.get("email")); append(md,"phone",personal.get("phone")); append(md,"location",personal.get("location"));
    section(md,"Summary",resume.get("summary")); section(md,"Experience",resume.get("experience"));
    section(md,"Education",resume.get("education")); section(md,"Skills",resume.get("skills"));
    section(md,"Projects",resume.get("projects")); section(md,"Achievements",resume.get("achievements"));
    section(md,"Certifications",resume.get("certifications")); section(md,"Custom",resume.get("custom"));
    return md.toString().trim()+"\n";
  }

  public String exportLatex(Map<String,Object> resume){
    Object source=resume.get("sourceContent");
    if("tex".equalsIgnoreCase(String.valueOf(resume.get("sourceFormat"))) && source!=null) return String.valueOf(source);
    String name=String.valueOf(map(resume.get("personal")).getOrDefault("name","Resume"));
    return "\\documentclass{article}\n\\begin{document}\n\\section*{"+escape(name)+"}\n"+
      "\\textbf{Summary}\n"+escape(String.valueOf(resume.getOrDefault("summary","")))+"\n\\end{document}\n";
  }

  private void section(StringBuilder b,String title,Object value){if(value!=null&&!String.valueOf(value).isBlank())b.append("## ").append(title).append("\n\n").append(value).append("\n\n");}
  private void append(StringBuilder b,String k,Object v){if(v!=null&&!String.valueOf(v).isBlank())b.append(v).append(" "); if(v!=null&&!String.valueOf(v).isBlank())b.append("\n");}
  private Map<String,Object> map(Object o){if(o instanceof Map<?,?> m){Map<String,Object> out=new LinkedHashMap<>();m.forEach((k,v)->out.put(String.valueOf(k),v));return out;}return new LinkedHashMap<>();}
  private String escape(String s){return s.replace("\\","\\textbackslash{}").replace("_","\\_").replace("&","\\&").replace("%","\\%").replace("#","\\#");}
}
