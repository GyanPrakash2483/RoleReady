package com.roleready.session;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuestUsageService {
  public static final int MAX_USES=3;
  private final GuestUsageRepository repository;
  public GuestUsageService(GuestUsageRepository repository){this.repository=repository;}

  @Transactional
  public boolean consume(String guestKey){
    GuestUsage usage=repository.findById(guestKey).orElseGet(()->new GuestUsage(guestKey));
    if(usage.getUsesCompleted()>=MAX_USES) return false;
    usage.setUsesCompleted(usage.getUsesCompleted()+1);
    repository.save(usage);
    return true;
  }

  public int remaining(String guestKey){
    return Math.max(0,MAX_USES-repository.findById(guestKey).map(GuestUsage::getUsesCompleted).orElse(0));
  }
}
