package remy.command;

import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Subclass of {@code Command} that terminate the program
 */
public class ExitCommand extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showBye();
    }
}
