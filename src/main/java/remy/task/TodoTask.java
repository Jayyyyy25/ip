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

    @Override
    public String getStatus() {
        return "[T]" + super.getStatus();
    }

    @Override
    public String toString() {
        return "T | " + super.toString();
    }
}
