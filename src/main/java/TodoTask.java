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
