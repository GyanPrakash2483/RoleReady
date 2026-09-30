package com.roleready.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class GuestUsageServiceTest {
  @Test void allowsThreeAndRejectsFourth(){
    GuestUsageRepository repo=mock(GuestUsageRepository.class);
    when(repo.findById("g")).thenReturn(Optional.of(new GuestUsage("g")));
    GuestUsageService service=new GuestUsageService(repo);
    GuestUsage usage=new GuestUsage("g");
    usage.setUsesCompleted(2);
    when(repo.findById("g")).thenReturn(Optional.of(usage));
    assertThat(service.consume("g")).isTrue();
    assertThat(service.consume("g")).isFalse();
  }
}
