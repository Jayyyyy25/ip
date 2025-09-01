import java.io.IOException;

public class EditCommand extends Command {
    private int editType; // 0 for unmark, 1 for mark
    private int taskInd;

    public EditCommand(int type, int ind) {
        this.editType = type;
        this.taskInd = ind;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RemyException {
        if (this.taskInd >= tasks.getSize()) {
            throw new InvalidArgumentException("Please provide a valid index to mark a task.");
        }

        if (this.editType == 0) {
            tasks.markAsUndone(this.taskInd);
            try {
                storage.updateLine(taskInd, tasks.getTaskString(this.taskInd));
            } catch (IOException e) {
                System.out.println("\t\t\tError updating task completeness: " + e.getMessage());
            }
            ui.showUnmark(tasks, this.taskInd);
        } else {
            tasks.markAsDone(this.taskInd);
            try {
                storage.updateLine(taskInd, tasks.getTaskString(this.taskInd));
            } catch (IOException e) {
                System.out.println("\t\t\tError updating task completeness: " + e.getMessage());
            }
            ui.showMark(tasks, this.taskInd);
        }
    }
}
