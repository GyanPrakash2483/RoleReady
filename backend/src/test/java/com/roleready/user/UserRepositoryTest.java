package com.roleready.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = {
    "spring.flyway.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserRepositoryTest {

  @Autowired
  private UserRepository users;

  @Test
  void savesAndFindsByEmail() {
    User user = new User();
    user.setEmail("test@example.com");
    user.setPasswordHash("hash");

    users.saveAndFlush(user);

    assertThat(users.findByEmail("test@example.com")).isPresent();
  }
}
