package com.eurodyn.qlack.observability.timeauthority.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing custom metrics in the Time Authority application. This service
 * creates and maintains Micrometer-based metrics that track various operations within the
 * application, such as method invocation counts. Metrics are registered with the
 * {@link MeterRegistry} and can be exported to monitoring systems like Prometheus.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsService {

  // The Micrometer meter registry used for registering and managing metrics.
  private final MeterRegistry meterRegistry;

  /**
   * Counter metric that tracks the number of times the {@code getEnvironmentTz} method is called.
   * This metric is automatically registered during service initialization.
   */
  @Getter
  private Counter environmentTzCounter;

  /**
   * Initializes and registers custom metrics on service startup. This method is automatically
   * invoked after dependency injection is complete and creates a counter metric for tracking
   * {@code getEnvironmentTz} method invocations.
   */
  @PostConstruct
  public void init() {
    log.info("Creating custom metrics.");
    environmentTzCounter = Counter.builder(
            "qlack.observability.getEnvironmentTz.count")
        .description("Number of times getEnvironmentTz method is called")
        .register(meterRegistry);
    log.info("Custom metrics created.");
  }
}
