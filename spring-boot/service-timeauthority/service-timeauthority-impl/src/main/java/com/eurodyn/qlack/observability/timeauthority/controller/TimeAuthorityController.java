package com.eurodyn.qlack.observability.timeauthority.controller;

import com.eurodyn.qlack.observability.timeauthority.client.dto.TimeResponse;
import com.eurodyn.qlack.observability.timeauthority.service.TimeAuthorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the Time Authority service endpoints. This controller exposes HTTP endpoints
 * for retrieving current time information and delegates business logic to the
 * {@link TimeAuthorityService}.
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TimeAuthorityController {

  // Service responsible for generating timestamp information.
  private final TimeAuthorityService timeAuthorityService;

  /**
   * Endpoint to generate and retrieve the current timestamp. This method handles GET requests to
   * the root path and returns the current time along with timezone information.
   *
   * @return a {@link TimeResponse} containing the current timestamp and timezone
   */
  @GetMapping
  public TimeResponse generateTimestamp() {
    log.info("Current time requested.");

    return timeAuthorityService.generateTimestamp();
  }
}
