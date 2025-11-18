package com.eurodyn.qlack.observability.todo.controller;

import com.eurodyn.qlack.observability.todo.dto.TodoItem;
import com.eurodyn.qlack.observability.todo.service.TodoService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling todo item operations. Provides endpoints for creating, reading, and
 * deleting todo items.
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TodoController {

  // The service for handling todo business logic.
  private final TodoService todoService;

  /**
   * Retrieves all todo items.
   *
   * @return A list of all todo items.
   */
  @GetMapping
  public List<TodoItem> getAllTodos() {
    log.info("Getting all todos.");

    return todoService.getAllTodos();
  }

  /**
   * Creates a new todo item.
   *
   * @param todoItem The todo item to create.
   * @return The created todo item with an assigned ID and timestamp.
   */
  @PostMapping
  public TodoItem createTodo(@RequestBody TodoItem todoItem) {
    log.info("Creating todo.");

    return todoService.createTodo(todoItem);
  }

  /**
   * Retrieves a todo item by its ID.
   *
   * @param id The UUID of the todo item.
   * @return The todo item if found, null otherwise.
   */
  @GetMapping("/{id}")
  public TodoItem getTodoById(@PathVariable UUID id) {
    log.info("Getting todo by id.");

    return todoService.getTodoById(id);
  }

  /**
   * Deletes a todo item by its ID.
   *
   * @param id The UUID of the todo item to delete.
   */
  @DeleteMapping("/{id}")
  public void deleteTodo(@PathVariable UUID id) {
    log.info("Deleting todo.");

    todoService.deleteTodo(id);
  }
}
