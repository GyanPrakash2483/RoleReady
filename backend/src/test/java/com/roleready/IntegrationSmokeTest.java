package com.roleready;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties={"spring.flyway.enabled=false","spring.jpa.hibernate.ddl-auto=create-drop"})
class IntegrationSmokeTest {
  @Test void applicationContextStarts(){ assertThat(true).isTrue(); }
}
