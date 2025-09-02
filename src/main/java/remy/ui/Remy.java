package remy.ui;

import remy.command.Command;

import remy.exception.InvalidArgumentException;
import remy.exception.RemyException;

import remy.task.TaskList;

import remy.util.Parser;
import remy.util.Storage;
import remy.util.Ui;

/**
 * Main class for running the Remy chatbot application
 */
public class Remy {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;

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
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Starts the Remy chatbot application
     *<p>
     * Displays a welcome message, processes user input in a loop,
     * and executes command until the exit command is issued
     * </p>
     */
    public void run() {
        ui.showWelcome();
        ui.showLine();
        boolean isRunning = true;
        while (isRunning) {
            try {
                String userInput = ui.readComment();
                ui.showLine();
                Command c = Parser.parseCommand(userInput);
                c.execute(tasks, ui, storage);
                isRunning = c.isRunning();
            } catch (RemyException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected Error: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Entry point of the chatbot application
     *
     * @param args command line arguments (not in use)
     */
    public static void main(String[] args) {
        new Remy("./data/remy.txt").run();
    }
}