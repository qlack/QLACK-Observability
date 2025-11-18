package com.eurodyn.qlack.observability.timeauthority.service;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for retrieving timezone information. This service provides methods to access
 * the system's default timezone and integrates with the metrics service to track method
 * invocations. Methods are instrumented with OpenTelemetry spans for distributed tracing.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TzService {

  /**
   * Service for managing and recording custom metrics.
   */
  private final MetricsService metricsService;

  /**
   * Retrieves the system's default timezone identifier. This method is instrumented with
   * OpenTelemetry for distributed tracing and increments a counter metric each time it is invoked.
   *
   * @return the timezone identifier of the system's default timezone (e.g., "America/New_York",
   * "Europe/Athens")
   */
  @WithSpan
  public String getEnvironmentTz() {
    metricsService.getEnvironmentTzCounter().increment();
    log.info("getEnvironmentTz method total count: {}",
        metricsService.getEnvironmentTzCounter().count());
    return java.time.ZoneId.systemDefault().getId();
  }
}
