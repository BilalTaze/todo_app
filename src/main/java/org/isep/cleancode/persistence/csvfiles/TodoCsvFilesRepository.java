package org.isep.cleancode.persistence.csvfiles;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class TodoCsvFilesRepository implements ITodoRepository {

    private final Path filePath;

    public TodoCsvFilesRepository() {
        String dir = System.getenv("APPDATA");
        if (dir == null) {
            dir = System.getProperty("user.home") + "/.todo-data"; // fallback for Linux/macOS
        } else {
            dir += "/todo-data";
        }

        File folder = new File(dir);
        if (!folder.exists()) folder.mkdirs();

        this.filePath = Paths.get(dir, "todos.csv");

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le fichier CSV", e);
        }
    }

    @Override
    public void addTodo(Todo todo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            String line = todo.getName() + ";" + (todo.getDueDate() != null ? todo.getDueDate() : "");
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Erreur d’écriture dans le fichier CSV", e);
        }
    }

    @Override
    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String name = parts[0];
                LocalDate date = parts.length > 1 && !parts[1].isEmpty() ? LocalDate.parse(parts[1]) : null;
                todos.add(new Todo(name, date));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier CSV", e);
        }
        return todos;
    }

    @Override
    public boolean existsByName(String name) {
        return getAllTodos().stream().anyMatch(t -> t.getName().equalsIgnoreCase(name));
    }
}
