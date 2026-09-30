package com.roleready.resume;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ResumeParser {
  private static final Set<String> EXTENSIONS=Set.of("pdf","docx","txt","md","markdown","tex","latex");
  private static final Pattern SECTION=Pattern.compile("(?im)^(?:#{1,6}\s*)?(summary|objective|profile|experience|work experience|education|skills|projects|achievements|certifications|awards|publications|languages|interests)\s*:?[ \t]*$");

  public ResumeDocument parse(MultipartFile file){
    validate(file);
    String name=Optional.ofNullable(file.getOriginalFilename()).orElse("resume");
    String ext=extension(name);
    try{
      String text=switch(ext){
        case "pdf" -> parsePdf(file.getBytes());
        case "docx" -> parseDocx(file.getInputStream());
        default -> new String(file.getBytes(),StandardCharsets.UTF_8);
      };
      return new ResumeDocument(ext,name,text,extractSections(text));
    }catch(IOException e){throw new IllegalArgumentException("Unable to parse resume file",e);}
  }

  private void validate(MultipartFile file){
    if(file.isEmpty()) throw new IllegalArgumentException("Empty resume file");
    if(file.getSize()>10*1024*1024) throw new IllegalArgumentException("Resume exceeds 10MB limit");
    String ext=extension(Optional.ofNullable(file.getOriginalFilename()).orElse(""));
    if(!EXTENSIONS.contains(ext)) throw new IllegalArgumentException("Unsupported resume format");
    try{
      byte[] b=file.getBytes();
      if("pdf".equals(ext) && !(b.length>=5 && new String(b,0,5,StandardCharsets.US_ASCII).equals("%PDF-")))
        throw new IllegalArgumentException("Invalid PDF file");
      if("docx".equals(ext) && !(b.length>=4 && (b[0]&255)==0x50 && (b[1]&255)==0x4b))
        throw new IllegalArgumentException("Invalid DOCX file");
    }catch(IOException e){throw new IllegalArgumentException("Unable to read resume file",e);}
  }

  private String parsePdf(byte[] bytes)throws IOException{
    try(var doc=Loader.loadPDF(bytes)){return new PDFTextStripper().getText(doc);}
  }

  private String parseDocx(InputStream in)throws IOException{
    try(var doc=new XWPFDocument(in)){
      StringBuilder out=new StringBuilder();
      doc.getParagraphs().forEach(p->out.append(p.getText()).append('\n'));
      return out.toString();
    }
  }

  private Map<String,String> extractSections(String text){
    List<Match> matches=new ArrayList<>();
    Matcher m=SECTION.matcher(text);
    while(m.find()) matches.add(new Match(m.group(1).toLowerCase(Locale.ROOT),m.start(),m.end()));
    Map<String,String> sections=new LinkedHashMap<>();
    for(int i=0;i<matches.size();i++){
      Match a=matches.get(i); int end=i+1<matches.size()?matches.get(i+1).start():text.length();
      sections.put(a.name(),text.substring(a.end(),end).trim());
    }
    return sections;
  }

  private record Match(String name,int start,int end){}
  private static String extension(String name){
    int i=name.lastIndexOf('.');
    return i<0?"":name.substring(i+1).toLowerCase(Locale.ROOT);
  }
}
