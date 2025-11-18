package com.eurodyn.qlack.observability.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for the Todo service. This is a Spring Boot application that provides REST
 * endpoints for managing todo items.
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.eurodyn.qlack.observability")
public class TodoApplication {

  /**
   * The main method that starts the Spring Boot application.
   *
   * @param args Command line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }
}
