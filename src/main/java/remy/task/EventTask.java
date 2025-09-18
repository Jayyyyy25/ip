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

    /**
     * Constructor for Event Task, to create a task with start and end date time value
     *
     * @param title Title of task
     * @param from Start time of event
     * @param to End time of event
     */
    public EventTask(String title, LocalDateTime from, LocalDateTime to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructor for Event Task, to create a task with start and end date time value
     *
     * @param title Title of task
     * @param from Start time of event
     * @param to End time of event
     * @param isDone Boolean value on whether the task is done
     */
    public EventTask(String title, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(title, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the status string of the event task, including its type, completion status, title,
     * and formatted start and end date/times.
     */
    @Override
    public String getStatus() {
        return String.format("[E]" + super.getStatus() + " (from: %s, to: %s)", this.fromDateString(),
                this.toDateString());
    }

    /**
     * Returns the string representation of the event task for storage purposes,
     * including its type, completion status, title, and start and end date/times.
     */
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
        boolean onEventDate = from.toLocalDate().equals(date) || to.toLocalDate().equals(date);
        boolean withinEventDate = from.toLocalDate().isBefore(date) && to.toLocalDate().isAfter(date);
        return onEventDate || withinEventDate;
    }
}
