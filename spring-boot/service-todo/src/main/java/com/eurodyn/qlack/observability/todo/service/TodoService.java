package com.eurodyn.qlack.observability.todo.service;

import com.eurodyn.qlack.observability.timeauthority.client.remote.TimeAuthorityServiceRemote;
import com.eurodyn.qlack.observability.todo.dto.TodoItem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Service class for managing todo items. Provides methods for CRUD operations on todo items,
 * integrating with the Time Authority service for timestamps.
 */
@Service
@RequiredArgsConstructor
public class TodoService {

  // In-memory list to store todo items.
  private final List<TodoItem> todos = new ArrayList<>();

  // Remote service for generating official timestamps.
  private final TimeAuthorityServiceRemote timeAuthorityServiceRemote;

  /**
   * Retrieves all todo items.
   *
   * @return A new list containing all todo items.
   */
  public List<TodoItem> getAllTodos() {
    return new ArrayList<>(todos);
  }

  /**
   * Creates a new todo item with an assigned ID and official timestamp.
   *
   * @param todoItem The todo item data to create from.
   * @return The newly created todo item.
   */
  public TodoItem createTodo(@RequestBody TodoItem todoItem) {
    TodoItem newTodo = TodoItem.builder()
        .id(UUID.randomUUID())
        .title(todoItem.getTitle())
        .description(todoItem.getDescription())
        .completed(false)
        .officialTimestamp(timeAuthorityServiceRemote.generateTimestamp().getCurrentTime())
        .build();
    todos.add(newTodo);

    return newTodo;
  }

  /**
   * Retrieves a todo item by its ID.
   *
   * @param id The UUID of the todo item.
   * @return The todo item if found, null otherwise.
   */
  public TodoItem getTodoById(UUID id) {
    return todos.stream()
        .filter(todo -> todo.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  /**
   * Deletes a todo item by its ID.
   *
   * @param id The UUID of the todo item to delete.
   */
  public void deleteTodo(UUID id) {
    todos.removeIf(todo -> todo.getId().equals(id));
  }
}
