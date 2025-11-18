package com.eurodyn.qlack.observability.timeauthority.service;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for retrieving the current date and time. This service provides methods to
 * access the current timestamp and is instrumented with OpenTelemetry spans for distributed
 * tracing. A random delay is introduced to simulate realistic processing time and make traces more
 * visible in monitoring tools.
 */
@Service
@RequiredArgsConstructor
public class TimeService {

  /**
   * Retrieves the current date and time. This method is instrumented with OpenTelemetry for
   * distributed tracing. A random delay of up to 1 second is introduced to simulate processing time
   * and make traces appear more realistic in observability tools.
   *
   * @return the current date and time as a {@link LocalDateTime} object
   */
  @WithSpan
  public LocalDateTime getCurrentTime() {
    // Introduce a random delay, so traces appear more realistic.
    try {
      Thread.sleep((long) (Math.random() * 1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    return LocalDateTime.now();
  }
}
