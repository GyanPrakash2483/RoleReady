package com.roleready.session;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
  private record Entry(RoleReadySession data, Instant expiresAt) {}
  private final Map<String,Entry> store=new ConcurrentHashMap<>();

  public String create(long ttlMinutes){
    String id=UUID.randomUUID().toString();
    store.put(id,new Entry(RoleReadySession.empty(),Instant.now().plusSeconds(ttlMinutes*60)));
    return id;
  }

  public RoleReadySession get(String id){
    Entry e=store.get(id);
    if(e==null || e.expiresAt().isBefore(Instant.now())) { store.remove(id); return null; }
    return e.data();
  }

  public void put(String id, RoleReadySession data, long ttlMinutes){
    store.put(id,new Entry(data,Instant.now().plusSeconds(ttlMinutes*60)));
  }

  public void delete(String id){ store.remove(id); }

  @Scheduled(fixedDelayString="\${app.session.cleanup-delay-ms:60000}")
  public void evictExpired(){
    Instant now=Instant.now();
    store.entrySet().removeIf(e->e.getValue().expiresAt().isBefore(now));
  }
}
