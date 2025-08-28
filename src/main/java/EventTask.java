import jdk.jfr.Event;

public class EventTask extends Task {
    protected String from;
    protected String to;

    public EventTask(String title, String from, String to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    public EventTask(String title, String from, String to, boolean isDone) {
        super(title, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getStatus() {
        String formatted = String.format("[E]" + super.getStatus() + " (from: %s, to: %s)", this.from, this.to);
        return formatted;
    }

    @Override
    public String toString() {
        return "E | " + super.toString() + " | " + this.from + " | " + this.to;
    }
}
