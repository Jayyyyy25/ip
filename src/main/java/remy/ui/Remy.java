package remy.ui;

import remy.command.Command;
import remy.exception.RemyException;
import remy.task.TaskList;
import remy.util.Parser;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Main class for running the Remy chatbot application
 */
public class Remy {
    private final Ui ui;
    private TaskList tasks;
    private final Storage storage;

    /**
     * Constructs a new {@code Remy} instance with the given file path for persistent storage
     *
     * @param filepath the path to the storage file for saving and loading tasks
     */
    public Remy(String filepath) {
        ui = new Ui();
        storage = new Storage(filepath);
        try {
            tasks = new TaskList(storage.load());
        } catch (RemyException e) {
            tasks = new TaskList();
        }
    }

    public String getWelcome() {
        return ui.showWelcome(tasks, ui, storage);
    }

    public String getResponse(String input) {
        try {
            Command c = Parser.parseCommand(input);
            return c.execute(tasks, ui, storage);
        } catch (RemyException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Unexpected Error: " + e.getMessage();
        }
    }
}
