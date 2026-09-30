package com.roleready.session;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class SessionServiceTest {
  @Test void createsRetrievesAndDeletesTemporarySession(){
    SessionService service=new SessionService();
    String id=service.create(10);
    assertThat(service.get(id)).isNotNull();
    service.delete(id);
    assertThat(service.get(id)).isNull();
  }
}
