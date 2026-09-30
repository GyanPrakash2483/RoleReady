package com.roleready.analysis;

import com.roleready.common.ApiResponse;
import com.roleready.session.GuestUsageService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {
  private final AnalysisService analysisService;
  private final GuestUsageService guestUsage;

  public AnalysisController(AnalysisService analysisService, GuestUsageService guestUsage){
    this.analysisService=analysisService;this.guestUsage=guestUsage;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String,Object>>> analyze(
      @RequestBody Map<String,Object> body, Principal principal,
      HttpServletRequest request, HttpServletResponse response) {
    if(principal==null){
      String key=guestKey(request,response);
      if(!guestUsage.consume(key)){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error("Guest limit reached. Please sign in to continue."));
      }
    }
    return ResponseEntity.ok(ApiResponse.ok(analysisService.analyze(body)));
  }

  private String guestKey(HttpServletRequest request,HttpServletResponse response){
    if(request.getCookies()!=null){
      for(Cookie c:request.getCookies()) if("rr_guest".equals(c.getName())) return c.getValue();
    }
    String key=UUID.randomUUID().toString();
    Cookie cookie=new Cookie("rr_guest",key);
    cookie.setHttpOnly(true); cookie.setSecure(request.isSecure()); cookie.setPath("/");
    cookie.setMaxAge(60*60*24*30);
    response.addCookie(cookie);
    return key;
  }

  @GetMapping("/current")
  public ResponseEntity<ApiResponse<Map<String,Object>>> current(){
    return ResponseEntity.ok(ApiResponse.ok(Map.of("status","empty")));
  }

  @PostMapping("/questions/answer")
  public ResponseEntity<ApiResponse<Map<String,Object>>> answer(@RequestBody Map<String,Object> body){
    return ResponseEntity.ok(ApiResponse.ok(Map.of("status","answers-stored")));
  }
}
