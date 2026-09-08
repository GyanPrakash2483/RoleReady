package com.roleready.session;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Temporary in-memory session store for V1 skeleton.
 * Production hosting can swap this for Redis/DB-backed entities
 * (ResumeSession, JobDescriptionSession, AnalysisSession...) with TTL.
 * Nothing here is permanent — data dies with the session (FR-PRIV-001..009, §41/42).
 */
@Service
public class SessionService {

  private record Entry(Map<String, Object> data, Instant expiresAt) {}

  private final Map<String, Entry> store = new ConcurrentHashMap<>();

  public String create(Map<String, Object> data, long ttlMinutes) {
    String id = UUID.randomUUID().toString();
    store.put(id, new Entry(data, Instant.now().plusSeconds(ttlMinutes * 60)));
    return id;
  }

  public void delete(String sessionId) {
    store.remove(sessionId);
  }

  @Scheduled(fixedDelayString = "${app.session.ttl-minutes:120}000")
  public void evictExpired() {
    Instant now = Instant.now();
    store.entrySet().removeIf(e -> e.getValue().expiresAt().isBefore(now));
  }
}
