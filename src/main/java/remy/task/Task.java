package remy.task;

import java.time.LocalDate;

/**
 * Represents a generic task
 * <p>
 *     Each task has a title and completion status.
 *     Subclasses of {@code Task} may include additional information
 * </p>
 */
public class Task {
    private String title;
    private boolean isDone;

    /**
     * Constructs a new task with the given title.
     * The task is initially not marked as done.
     */
    public Task(String title) {
        this.title = title;
        this.isDone = false;
    }

    /**
     * Constructs a new task with the given title and completion status
     */
    public Task(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon and title of the task
     * <pre>
     * [X] title    // if completed
     * [ ] title    // if not completed
     * </pre>
     * <p>
     *     Subclasses override to add a type prefix:
     *     <ul>
     *          <li>{@code [T] ...} for to-do tasks</li>
     *          <li>{@code [D] ...} for deadline tasks</li>
     *          <li>{@code [E] ...} for event tasks</li>
     *      </ul>
     * </p>
     */
    public String getStatus() {
        return (isDone ? "[X] " : "[ ] ") + title;
    }

    /**
     * Marks the task as done
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Unmark the task as done
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Returns the status icon and title of the task
     * in the format to be stored in storage file
     * <pre>
     * | 1 | title    // if completed
     * | 0 | title    // if not completed
     * </pre>
     * <p>
     *     Subclasses override to add a type prefix:
     *     <ul>
     *          <li>{@code T | ...} for to-do tasks</li>
     *          <li>{@code D | ...} for deadline tasks</li>
     *          <li>{@code E | ...} for event tasks</li>
     *      </ul>
     * </p>
     */
    @Override
    public String toString() {
        return (isDone ? "1" : "0") + " | " + title;
    }

    /**
     * Checks whether this task occurs on the specified date
     * <p>
     * By default, a generic Task does not have a date, so this always returns false.
     * </p>
     *
     * @param date the date to check
     * @return false for a generic Task
     */
    public boolean isCovered(LocalDate date) {
        return false;
    }
}
