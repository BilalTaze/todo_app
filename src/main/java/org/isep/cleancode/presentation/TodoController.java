package org.isep.cleancode.presentation;

import com.google.gson.*;
import org.isep.cleancode.application.TodoManager;
// import org.isep.cleancode.persistence.inmemory.TodoInMemoryRepository;
import org.isep.cleancode.persistence.csvfiles.TodoCsvFilesRepository;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Map;

public class TodoController {

    private static final Gson gson = new Gson();
    private final TodoManager todoManager = new TodoManager(new TodoCsvFilesRepository());

    public Object getAllTodos(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(todoManager.getAllTodos());
    }

    public Object createTodo(Request req, Response res) {
        try {
            Map<String, String> body = gson.fromJson(req.body(), Map.class);
            String name = body.get("name");
            String dueDateStr = body.get("dueDate");

            LocalDate dueDate = null;
            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                dueDate = LocalDate.parse(dueDateStr);
            }

            todoManager.createTodo(name, dueDate);

            res.status(201);
            return gson.toJson("Todo créé avec succès");

        } catch (Exception e) {
            res.status(400);
            return gson.toJson(e.getMessage());
        }
    }
}
