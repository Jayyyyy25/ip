package remy.command;

import java.util.List;

import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<String> list = tasks.getListing(null, keyword);
        ui.showListing(list, 2);
    }
}
