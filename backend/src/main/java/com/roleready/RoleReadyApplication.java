package com.roleready;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RoleReadyApplication {
  public static void main(String[] args) {
    SpringApplication.run(RoleReadyApplication.class, args);
  }
}
