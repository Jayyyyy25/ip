package remy.command;

import java.io.IOException;
import java.time.LocalDateTime;

import remy.task.DeadlineTask;
import remy.task.EventTask;
import remy.task.Task;
import remy.task.TaskList;
import remy.task.TodoTask;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Subclass for {@code Command} that add a new task to the list
 */
public class AddCommand extends Command {
    private String title;
    private LocalDateTime deadline;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;

    /**
     * Constructor for add task command, by using Varargs to differentiate which task type is added
     *
     * @param title title of the task
     * @param dates use number of dates inputted to differentiate type of date inputted
     */
    public AddCommand(String title, LocalDateTime ... dates) {
        this.title = title;
        if (dates.length == 2) {
            this.eventStartTime = dates[0];
            this.eventEndTime = dates[1];
            this.deadline = null;
        } else if (dates.length == 1) {
            this.eventStartTime = null;
            this.eventEndTime = null;
            this.deadline = dates[0];
        } else if (dates.length == 0) {
            this.eventStartTime = null;
            this.eventEndTime = null;
            this.deadline = null;
        } else {
            assert false : "Invalid argument for add command";
        }
    }

    @SuppressWarnings("checkstyle:Regexp")
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task task;
        if (deadline == null && eventStartTime == null && eventEndTime == null) {
            task = new TodoTask(title);
        } else if (deadline != null) {
            task = new DeadlineTask(title, deadline);
        } else {
            task = new EventTask(title, eventStartTime, eventEndTime);
        }
        int taskIdx = tasks.addItem(task);
        assert taskIdx <= tasks.getSize() : "Invalid task index returned";

        try {
            storage.appendLine(task.toString());
        } catch (IOException e) {
            System.out.println("\t\t\tError adding new remy.task: " + e.getMessage());
        }

        return ui.showAdded(tasks, taskIdx);
    }
}
