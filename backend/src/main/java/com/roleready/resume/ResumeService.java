package com.roleready.resume;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {
  private final ResumeParser parser;
  private volatile ResumeDocument current;

  public ResumeService(ResumeParser parser){this.parser=parser;}

  public Map<String,Object> parseUpload(MultipartFile file){
    current=parser.parse(file);
    Map<String,Object> result=new LinkedHashMap<>();
    result.put("filename",current.originalFilename());
    result.put("format",current.format());
    result.put("text",current.rawText());
    result.put("sections",current.sections());
    return result;
  }

  public Map<String,Object> create(Map<String,Object> resume){ current=null; return resume; }
  public Map<String,Object> current(){
    if(current==null) return Map.of("status","empty");
    return Map.of("filename",current.originalFilename(),"format",current.format(),"text",current.rawText(),"sections",current.sections());
  }
  public Map<String,Object> update(Map<String,Object> resume){ return resume; }
}
