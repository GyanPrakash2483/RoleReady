package com.roleready.session;

import com.roleready.common.ApiResponse;
import com.roleready.config.AppProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {
  private final SessionService sessions;
  private final AppProperties props;

  public SessionController(SessionService sessions, AppProperties props){this.sessions=sessions;this.props=props;}

  @PostMapping
  public ResponseEntity<ApiResponse<String>> create(){
    return ResponseEntity.ok(ApiResponse.ok(sessions.create(props.session().ttlMinutes())));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<RoleReadySession>> get(@PathVariable String id){
    RoleReadySession session=sessions.get(id);
    if(session==null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(ApiResponse.ok(session));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
    sessions.delete(id);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }
}
