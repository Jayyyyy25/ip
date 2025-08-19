public class DeadlineTask extends Task {
    protected String by;

    public DeadlineTask(String title, String by) {
        super(title);
        this.by = by;
    }

    @Override
    public String getStatus() {
        String formatted = String.format("[D]" + super.getStatus() + "(by: %s)", this.by);
        return formatted;
    }
}
