package com.eurodyn.qlack.observability.timeauthority.service;

import com.eurodyn.qlack.observability.timeauthority.client.dto.TimeResponse;
import io.micrometer.core.annotation.Timed;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Main service class for the Time Authority service. This service orchestrates the generation of
 * timestamp information by coordinating between the {@link TimeService} and {@link TzService} to
 * provide complete time and timezone data. All methods are instrumented with OpenTelemetry spans
 * for distributed tracing and Micrometer metrics for performance monitoring.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TimeAuthorityService {

  // Service responsible for retrieving timezone information.
  private final TzService tzService;

  // Service responsible for retrieving the current time.
  private final TimeService timeService;

  /**
   * Generates a complete timestamp response containing the current time and timezone. This method
   * is instrumented with both OpenTelemetry (for distributed tracing) and Micrometer (for execution
   * time metrics). It combines data from the time service and timezone service to create a
   * comprehensive response.
   *
   * @return a {@link TimeResponse} containing the current timestamp and timezone information
   */
  @WithSpan
  @Timed(value = "qlack.observability.generateTimestamp.time",
      description = "Time taken to execute generateTimestamp method")
  public TimeResponse generateTimestamp() {

    return TimeResponse.builder()
        .currentTime(timeService.getCurrentTime())
        .timezone(tzService.getEnvironmentTz())
        .build();
  }
}
