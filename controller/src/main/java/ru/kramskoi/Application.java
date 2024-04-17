package ru.kramskoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ru.kramskoi.repository")
@EntityScan("ru.kramskoi.entity")
@SpringBootApplication
@SpringBootConfiguration
public class Application {
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Application.class, args);
  }
}