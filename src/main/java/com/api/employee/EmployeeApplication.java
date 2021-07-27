package com.api.employee;

import com.api.employee.entity.EmployeeEntity;
import com.api.employee.service.PushService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class EmployeeApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(EmployeeApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(PushService pushService) {
    return args -> {
      ObjectMapper mapper = new ObjectMapper();
      TypeReference<List<EmployeeEntity>> typeReference = new TypeReference<>() {};
      InputStream inputStream = TypeReference.class.getResourceAsStream("/json/mockdata.json");
      try {
        List<EmployeeEntity> users = mapper.readValue(inputStream, typeReference);
        pushService.save(users);
        LOGGER.info("Users Saved!");

      } catch (IOException e) {
        LOGGER.error("Unable to save users {}", e.getMessage());
      }
    };
  }
}
