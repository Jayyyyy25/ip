package remy.command;

import java.io.IOException;
import remy.task.TaskList;
import remy.task.Task;
import remy.util.Storage;
import remy.util.Ui;

public class DeleteCommand extends Command {
    private int ind;

    public DeleteCommand(int ind) {
        this.ind = ind;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.deleteItem(this.ind);
        try {
            storage.deleteLine(this.ind);
        } catch (IOException e) {
            System.out.println("\t\t\tError deleting remy.task: " + e.getMessage());
        }
        ui.showDeleted(tasks, task);
    }
}
