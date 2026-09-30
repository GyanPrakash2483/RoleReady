package com.roleready.session;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="guest_usage")
public class GuestUsage {
  @Id @Column(name="guest_key",length=128) private String guestKey;
  @Column(name="uses_completed",nullable=false) private int usesCompleted;
  @Column(name="updated_at",nullable=false) private Instant updatedAt=Instant.now();
  public GuestUsage(){}
  public GuestUsage(String key){guestKey=key;}
  public String getGuestKey(){return guestKey;}
  public int getUsesCompleted(){return usesCompleted;}
  public void setUsesCompleted(int n){usesCompleted=n;updatedAt=Instant.now();}
}
