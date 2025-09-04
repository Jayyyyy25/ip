package remy.command;

import java.time.LocalDate;
import java.util.List;

import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Subclass of {@code Command} that list out all tasks in the list
 */
public class ListCommand extends Command {
    private LocalDate specifiedDate;

    public ListCommand() {
        specifiedDate = null;
    }

    public ListCommand(LocalDate date) {
        specifiedDate = date;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        List<String> list = tasks.getListing(specifiedDate, "");
        if (specifiedDate == null) {
            return ui.showListing(list, 0);
        } else {
            return ui.showListing(list, 1);
        }
    }
}
