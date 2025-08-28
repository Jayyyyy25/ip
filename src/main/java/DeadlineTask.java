import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    protected LocalDateTime by;

    public DeadlineTask(String title, LocalDateTime by) {
        super(title);
        this.by = by;
    }

    public DeadlineTask(String title, LocalDateTime by, boolean isDone) {
        super(title, isDone);
        this.by = by;
    }

    @Override
    public String getStatus() {
        return String.format("[D]" + super.getStatus() + " (by: %s)", this.dateString());
    }

    @Override
    public String toString() {
        return "D | " + super.toString() + " | " + this.dateString();
    }

    public String dateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return this.by.format(formatter);
    }

    @Override
    public boolean isCovered(LocalDate date) {
        return this.by.toLocalDate().equals(date);
    }
}
