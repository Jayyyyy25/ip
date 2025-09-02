package remy.command;

import java.io.IOException;

import remy.task.TaskList;

import remy.util.Storage;
import remy.util.Ui;

import remy.exception.InvalidArgumentException;
import remy.exception.RemyException;

public class EditCommand extends Command {
    private int editType; // 0 for unmark, 1 for mark
    private int taskInd;

    public EditCommand(int type, int ind) {
        this.editType = type;
        this.taskInd = ind;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RemyException {
        if (taskInd >= tasks.getSize()) {
            throw new InvalidArgumentException("Please provide a valid index to mark a remy.task.");
        }

        if (editType == 0) {
            tasks.markAsUndone(taskInd);
            try {
                storage.updateLine(taskInd, tasks.getTaskString(taskInd));
            } catch (IOException e) {
                System.out.println("\t\t\tError updating remy.task completeness: " + e.getMessage());
            }
            ui.showUnmark(tasks, taskInd);
        } else { // else statement is used and do not consider value other than 0 and 1 is because EditCommand constructor only called internally
            tasks.markAsDone(taskInd);
            try {
                storage.updateLine(taskInd, tasks.getTaskString(taskInd));
            } catch (IOException e) {
                System.out.println("\t\t\tError updating remy.task completeness: " + e.getMessage());
            }
            ui.showMark(tasks, taskInd);
        }
    }
}
