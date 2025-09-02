package remy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public EventTask(String title, LocalDateTime from, LocalDateTime to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    public EventTask(String title, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(title, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getStatus() {
        return String.format("[E]" + super.getStatus() + " (from: %s, to: %s)", this.fromDateString(),
                this.toDateString());
    }

    @Override
    public String toString() {
        return "E | " + super.toString() + " | " + this.fromDateString() + " | " + this.toDateString();
    }

    public String fromDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return from.format(formatter);
    }

    public String toDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return to.format(formatter);
    }

    @Override
    public boolean isCovered(LocalDate date) {
        return from.toLocalDate().equals(date) || to.toLocalDate().equals(date) ||
                (from.toLocalDate().isBefore(date) && to.toLocalDate().isAfter(date));
    }
}
