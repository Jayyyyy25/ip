public class Task {
    private String title;
    private boolean isDone;

    public Task(String title) {
        this.title = title;
        this.isDone = false;
    }

    public Task(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    public String getStatus() {
        return (isDone ? "[X] " : "[ ] ") + this.title;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public static Task parseTask(String data) throws InvalidArgumentException {
        String[] taskTypeSplit = data.split("\\|", 2);
        String taskType = taskTypeSplit[0].trim();
        String[] isDoneSplit = taskTypeSplit[1].split("\\|", 2);
        String isDoneStr = isDoneSplit[0].trim();
        String taskInfo = isDoneSplit[1].trim();
        int isDone;
        String title;
        switch (taskType) {
        case "T":
            isDone = Integer.parseInt(isDoneStr);
            title = taskInfo;
            return new TodoTask(title, isDone != 0);
        case "D":
            isDone = Integer.parseInt(isDoneStr);
            String[] titleSplit = taskInfo.split("\\|", 2);
            title = titleSplit[0].trim();
            String ddl = titleSplit[1].trim();
            return new DeadlineTask(title, ddl, isDone != 0);
        case "E":
            isDone = Integer.parseInt(isDoneStr);
            String[] fromSplit = taskInfo.split("\\|", 2);
            title = fromSplit[0].trim();
            String [] toSplit = fromSplit[1].split("\\|", 2);
            String from = toSplit[0].trim();
            String to = toSplit[1].trim();
            return new EventTask(title, from, to, isDone != 0);
        default:
            throw new InvalidArgumentException("Invalid string input");
        }
    }

    @Override
    public String toString() {
        return (this.isDone ? "1" : "0") + " | " + this.title;
    }
}
