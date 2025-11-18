package com.eurodyn.qlack.observability.timeauthority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Time Authority service.
 * This Spring Boot application provides a centralized service for retrieving
 * current time information along with timezone data. The service is instrumented
 * with observability features including metrics, tracing, and logging.
 */
@SpringBootApplication
public class TimeAuthorityApplication {

  /**
   * Main entry point for the Time Authority application.
   * Bootstraps the Spring Boot application context and starts the embedded server.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(TimeAuthorityApplication.class, args);
  }
}

