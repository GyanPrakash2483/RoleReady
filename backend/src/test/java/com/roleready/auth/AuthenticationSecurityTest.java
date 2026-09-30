package com.roleready.auth;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthenticationSecurityTest {
  @Test void passwordsAreOneWayHashed(){
    var encoder=new BCryptPasswordEncoder();
    String raw="StrongPassword123";
    String hash=encoder.encode(raw);
    assertThat(hash).isNotEqualTo(raw);
    assertThat(encoder.matches(raw,hash)).isTrue();
  }
}
