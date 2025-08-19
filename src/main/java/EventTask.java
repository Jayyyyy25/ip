public class EventTask extends Task {
    protected String from;
    protected String to;

    public EventTask(String title, String from, String to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getStatus() {
        String formatted = String.format("[E]" + super.getStatus() + " (from: %s, to: %s)", this.from, this.to);
        return formatted;
    }
}
