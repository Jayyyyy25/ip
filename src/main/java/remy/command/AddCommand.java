package remy.command;

import java.io.IOException;

import java.time.LocalDateTime;

import remy.task.TaskList;
import remy.task.Task;
import remy.task.TodoTask;
import remy.task.DeadlineTask;
import remy.task.EventTask;

import remy.util.Storage;
import remy.util.Ui;

public class AddCommand extends Command {
    private String title;
    private LocalDateTime deadline;
    private LocalDateTime from;
    private LocalDateTime to;

    public AddCommand(String title) {
        this.title = title;
        this.deadline = null;
        this.from = null;
        this.to = null;
    }

    public AddCommand(String title, LocalDateTime deadline) {
        this.title = title;
        this.deadline = deadline;
        this.from = null;
        this.to = null;
    }

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
        int taskInd = tasks.addItem(task);

        try {
            storage.appendLine(task.toString());
        } catch (IOException e) {
            System.out.println("\t\t\tError adding new remy.task: " + e.getMessage());
        }

        return ui.showAdded(tasks, taskInd);
    }
}
