package com.roleready;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties={"spring.flyway.enabled=false","spring.jpa.hibernate.ddl-auto=create-drop"})
class IntegrationSmokeTest {
  @Autowired RoleReadyApplication application;
  @Test void applicationContextStarts(){ assertThat(application).isNotNull(); }
}
