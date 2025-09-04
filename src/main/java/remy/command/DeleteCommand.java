package remy.command;

import java.io.IOException;

import remy.task.Task;
import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Subclass of {@code Command} that delete a current task from the list
 */
public class DeleteCommand extends Command {
    private int ind;

    public DeleteCommand(int ind) {
        this.ind = ind;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.deleteItem(ind);
        try {
            storage.deleteLine(ind);
        } catch (IOException e) {
            System.out.println("\t\t\tError deleting remy.task: " + e.getMessage());
        }
        return ui.showDeleted(tasks, task);
    }
}
