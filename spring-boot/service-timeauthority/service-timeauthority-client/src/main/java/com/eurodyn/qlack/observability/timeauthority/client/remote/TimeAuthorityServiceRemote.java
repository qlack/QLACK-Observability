package com.eurodyn.qlack.observability.timeauthority.client.remote;

import com.eurodyn.qlack.observability.timeauthority.client.dto.TimeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign client interface for communicating with the Time Authority service. This interface provides
 * remote access to the Time Authority service endpoints using Spring Cloud OpenFeign for
 * declarative REST client functionality.
 * <p>
 * The service is configured to connect to the Time Authority service running on localhost:9081 by
 * default.
 */
@FeignClient(
    name = "openfeign.service-timeauthority",
    url = "http://localhost:9081"
)
public interface TimeAuthorityServiceRemote {

  /**
   * Generates and retrieves the current timestamp from the Time Authority service. This method
   * calls the root endpoint of the Time Authority service to obtain the current time along with
   * timezone information.
   *
   * @return a {@link TimeResponse} object containing the current timestamp and timezone
   */
  @GetMapping("/")
  TimeResponse generateTimestamp();
}
