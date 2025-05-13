package org.isep.cleancode.application;

import org.isep.cleancode.Todo;

import java.time.LocalDate;
import java.util.List;

public class TodoManager {
    private final ITodoRepository repository;

    public TodoManager(ITodoRepository repository) {
        this.repository = repository;
    }

    public void createTodo(String name, LocalDate dueDate) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Le nom est requis.");
        }

        if (name.length() > 63) {
            throw new Exception("Le nom doit faire moins de 64 caractères.");
        }

        if (repository.existsByName(name)) {
            throw new Exception("Le nom est déjà utilisé.");
        }

        Todo todo = new Todo(name, dueDate);
        repository.addTodo(todo);
    }

    public List<Todo> getAllTodos() {
        return repository.getAllTodos();
    }
}
