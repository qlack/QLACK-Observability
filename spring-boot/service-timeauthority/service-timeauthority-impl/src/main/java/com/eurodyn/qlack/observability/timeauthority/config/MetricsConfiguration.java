package com.eurodyn.qlack.observability.timeauthority.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up metrics collection in the Time Authority service. This
 * configuration enables aspect-oriented programming (AOP) support for Micrometer metrics, allowing
 * the use of {@code @Timed} annotation on methods to automatically track their execution time.
 */
@Configuration
public class MetricsConfiguration {

  /**
   * Creates and configures a {@link TimedAspect} bean to enable support for the {@code @Timed}
   * annotation.
   *
   * @param registry the {@link MeterRegistry} used to register and manage metrics
   * @return a configured {@link TimedAspect} instance
   */
  @Bean
  public TimedAspect timedAspect(MeterRegistry registry) {
    return new TimedAspect(registry);
  }
}

