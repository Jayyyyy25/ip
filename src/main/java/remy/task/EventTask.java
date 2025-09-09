package remy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An EventTask represents a task that has a start and end date/time.
 * It extends the base Task class and adds "from" and "to" date/times.
 */
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

    /**
     * Returns the formatted string of the start date/time.
     */
    public String fromDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return from.format(formatter);
    }

    /**
     * Returns the formatted string of the end date/time.
     */
    public String toDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return to.format(formatter);
    }

    /**
     * Checks if the specified date falls within the event's start and end dates (inclusive).
     *
     * @param date the date to check
     * @return true if the date is on or between the event's start and end dates, false otherwise
     */
    @Override
    public boolean isCovered(LocalDate date) {
        return from.toLocalDate().equals(date) || to.toLocalDate().equals(date) || (
                from.toLocalDate().isBefore(date) && to.toLocalDate().isAfter(date));
    }
}
