package remy.task;

/**
 * A TodoTask represents a simple task without a date or time.
 * It extends the base Task class.
 */
public class TodoTask extends Task {
    public TodoTask(String title) {
        super(title);
    }

    public TodoTask(String title, boolean isDone) {
        super(title, isDone);
    }

    /**
     * Returns the status string of the todo task, including its type, completion status, and title.
     */
    @Override
    public String getStatus() {
        return "[T]" + super.getStatus();
    }

    /**
     * Returns the string representation of the todo task for storage purposes,
     * including its type, completion status, and title.
     */
    @Override
    public String toString() {
        return "T | " + super.toString();
    }
}
