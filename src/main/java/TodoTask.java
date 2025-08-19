public class TodoTask extends Task {
    public TodoTask(String title) {
        super(title);
    }

    @Override
    public String getStatus() {
        return "[T]" + super.getStatus();
    }
}
