package remy.command;

import java.time.LocalDate;
import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

public class ListCommand extends Command {
    private LocalDate specifiedDate;

    public ListCommand() {
        specifiedDate = null;
    }

    public ListCommand(LocalDate date) {
        specifiedDate = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.getListing(specifiedDate);
    }
}
