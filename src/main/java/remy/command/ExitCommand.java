package remy.command;

import remy.task.TaskList;

import remy.util.Storage;
import remy.util.Ui;

public class ExitCommand extends Command {
    private boolean isRunning;

    public ExitCommand() {
        isRunning = true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        isRunning = false;
        ui.showBye();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
