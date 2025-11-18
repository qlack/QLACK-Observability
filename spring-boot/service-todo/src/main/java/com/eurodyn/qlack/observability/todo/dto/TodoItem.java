package com.eurodyn.qlack.observability.todo.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a todo item. Contains fields for the item's ID, title,
 * description, completion status, and official timestamp.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoItem {

  // The unique identifier of the todo item.
  private UUID id;

  // The title of the todo item.
  private String title;

  // The description of the todo item.
  private String description;

  // Indicates whether the todo item is completed.
  private boolean completed;

  // The official timestamp when the todo item was created.
  private LocalDateTime officialTimestamp;
}
