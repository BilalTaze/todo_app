package org.isep.cleancode;

import java.time.LocalDate;

public class Todo {

    // this Todo class should be completed to achieve Step 1
    private String name;

    private LocalDate dueDate;

    public Todo(String name, LocalDate dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
    