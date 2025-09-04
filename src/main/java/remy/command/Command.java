package remy.command;

import remy.exception.RemyException;

import remy.task.TaskList;

import remy.util.Storage;
import remy.util.Ui;

/**
 * Represents an abstract command in the Remy chatbot application.
 *
 * <p>All concrete commands (subclasses) must implement the {@link #execute(TaskList, Ui, Storage)}
 * method to define their specific behavior. Commands are executed by the {@code Remy} application
 * when a user inputs a corresponding instruction.</p>
 *
 * <p>The {@link #isRunning()} method can be overridden by commands that intend to stop the program,
 * such as an exit command. By default, it returns {@code true}.</p>
 */
public abstract class Command {

    /**
     * Executes this command using the provided task list, user interface, and storage.
     *
     * @param tasks   the task list for saving, loading and operating on tasks
     * @param ui      the user interface for displaying messages
     * @param storage the storage to update task data
     * @return response from the Remy
     * @throws RemyException if an error occurs during execution
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws RemyException;
}
