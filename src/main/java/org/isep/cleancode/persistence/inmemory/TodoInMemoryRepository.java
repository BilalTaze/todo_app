package org.isep.cleancode.persistence.inmemory;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;

import java.util.ArrayList;
import java.util.List;

public class TodoInMemoryRepository implements ITodoRepository {

    private final List<Todo> todos = new ArrayList<>();

    @Override
    public void addTodo(Todo todo) {
        todos.add(todo);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todos;
    }

    @Override
    public boolean existsByName(String name) {
        return todos.stream().anyMatch(t -> t.getName().equalsIgnoreCase(name));
    }
}
