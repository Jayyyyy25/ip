package remy.command;
import remy.exception.RemyException;
import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

public abstract class Command {
    public Command() {

    }
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws RemyException;

    public boolean isRunning() {
        return true;
    }
}
