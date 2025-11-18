package com.eurodyn.qlack.observability.timeauthority.client.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a time response from the Time Authority service. This class
 * encapsulates the current time along with its associated timezone information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeResponse {

  /**
   * The current date and time returned by the Time Authority service. This value represents the
   * precise moment when the time was retrieved.
   */
  private LocalDateTime currentTime;

  /**
   * The timezone identifier for the current time. This typically follows the IANA Time Zone
   * Database format (e.g., "America/New_York", "Europe/Athens").
   */
  private String timezone;
}

