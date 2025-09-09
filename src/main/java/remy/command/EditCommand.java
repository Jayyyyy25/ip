package remy.command;

import java.io.IOException;

import remy.exception.InvalidArgumentException;
import remy.exception.RemyException;
import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Subclass of {@code Command} that edit a status of a current task in the list
 */
public class EditCommand extends Command {
    private int editType; // 0 for unmark, 1 for mark
    private int taskInd;

    /**
     * Constructor for a edit command by providing edit type and index of task to be edited
     *
     * @param type edit type of the command, where 0 indicates unmark a task as done, 1 indicates mark a task as done
     * @param ind index of the task to be edited
     */
    public EditCommand(int type, int ind) {
        this.editType = type;
        this.taskInd = ind;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws RemyException {
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
            return ui.showUnmark(tasks, taskInd);
        } else if (editType == 1) {
            tasks.markAsDone(taskInd);
            try {
                storage.updateLine(taskInd, tasks.getTaskString(taskInd));
            } catch (IOException e) {
                System.out.println("\t\t\tError updating remy.task completeness: " + e.getMessage());
            }
            return ui.showMark(tasks, taskInd);
        } else {
            assert false : "Invalid edit type";
            return "";
        }
    }
}
