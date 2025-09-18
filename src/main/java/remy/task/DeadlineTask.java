package remy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A DeadlineTask represents a task that has a specific deadline.
 * It extends the base Task class and adds a "by" date.
 */
public class DeadlineTask extends Task {
    protected LocalDateTime by;

    /**
     * Constructor for Deadline Task, to create a task with deadline
     *
     * @param title Title of task
     * @param by Deadline of the task
     */
    public DeadlineTask(String title, LocalDateTime by) {
        super(title);
        this.by = by;
    }

    /**
     * Constructor for Deadline Task, to create a task with deadline
     *
     * @param title Title of task
     * @param by Deadline of the task
     * @param isDone Boolean value on whether the task is done, false as default
     */
    public DeadlineTask(String title, LocalDateTime by, boolean isDone) {
        super(title, isDone);
        this.by = by;
    }

    /**
     * Returns the status string of the deadline task, including its type, completion status, title,
     * and formatted deadline date.
     */
    @Override
    public String getStatus() {
        return String.format("[D]" + super.getStatus() + " (by: %s)", this.dateString());
    }

    /**
     * Returns the string representation of the deadline task for storage purposes,
     * including its type, completion status, title, and deadline date.
     */
    @Override
    public String toString() {
        return "D | " + super.toString() + " | " + this.dateString();
    }

    /**
     * Returns the formatted string of deadline date
     */
    public String dateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return by.format(formatter);
    }

    /**
     * Checks if the specified date matches the deadline date.
     *
     * @param date the date to check
     * @return true if the task's deadline is on the specified date, false otherwise
     */
    @Override
    public boolean isCovered(LocalDate date) {
        return by.toLocalDate().equals(date);
    }
}
