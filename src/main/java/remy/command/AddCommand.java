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
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructor for add command to todo task
     *
     * @param title
     */
    public AddCommand(String title) {
        this.title = title;
        this.deadline = null;
        this.from = null;
        this.to = null;
    }

    /**
     * Constructor for add command to deadline task
     *
     * @param title
     * @param deadline
     */
    public AddCommand(String title, LocalDateTime deadline) {
        this.title = title;
        this.deadline = deadline;
        this.from = null;
        this.to = null;
    }

    /**
     * Constructor for add comment to event task
     *
     * @param title
     * @param from
     * @param to
     */
    public AddCommand(String title, LocalDateTime from, LocalDateTime to) {
        this.title = title;
        this.deadline = null;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task task;
        if (deadline == null && from == null && to == null) {
            task = new TodoTask(title);
        } else if (deadline != null) {
            task = new DeadlineTask(title, deadline);
        } else {
            task = new EventTask(title, from, to);
        }
        int taskIdx = tasks.addItem(task);

        try {
            storage.appendLine(task.toString());
        } catch (IOException e) {
            System.out.println("\t\t\tError adding new remy.task: " + e.getMessage());
        }

        return ui.showAdded(tasks, taskIdx);
    }
}
