package com.roleready.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AiRateLimitFilter extends OncePerRequestFilter {
  private record Window(long startedAt,int count){}
  private final ConcurrentHashMap<String,Window> windows=new ConcurrentHashMap<>();
  private static final int LIMIT=60;
  private static final long WINDOW_MS=60_000;

  @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)
      throws ServletException,IOException {
    if(!isAiEndpoint(req.getRequestURI()) || !"POST".equals(req.getMethod())) { chain.doFilter(req,res); return; }
    String key=req.getRemoteAddr();
    long now=Instant.now().toEpochMilli();
    Window next=windows.compute(key,(k,w)->w==null||now-w.startedAt()>=WINDOW_MS?new Window(now,1):new Window(w.startedAt(),w.count()+1));
    if(next.count()>LIMIT){res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());res.setContentType("application/json");res.getWriter().write("{"error":"AI request rate limit exceeded"}");return;}
    chain.doFilter(req,res);
  }

  private boolean isAiEndpoint(String p){
    return p.equals("/api/analysis")||p.equals("/api/job-description/analyze")||p.equals("/api/questions")||
      p.equals("/api/suggestions")||p.equals("/api/optimization");
  }
}
