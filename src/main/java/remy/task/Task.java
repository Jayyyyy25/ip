package remy.task;

import remy.exception.InvalidArgumentException;
import remy.util.Parser;
import java.time.LocalDate;

public class Task {
    private String title;
    private boolean isDone;

    public Task(String title) {
        this.title = title;
        this.isDone = false;
    }

    public Task(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    public String getStatus() {
        return (isDone ? "[X] " : "[ ] ") + this.title;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return (this.isDone ? "1" : "0") + " | " + this.title;
    }

    public boolean isCovered(LocalDate date) {
        return false;
    }
}
