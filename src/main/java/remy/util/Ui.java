package remy.util;

import java.util.List;
import java.util.Scanner;

import remy.command.ListCommand;
import remy.task.Task;
import remy.task.TaskList;

/**
 * Handles all user interactions such as displaying messages,
 * reading input, and showing feedback when tasks are modified.
 */
public class Ui {
    private static Scanner scanner = new Scanner(System.in);
    private String name;

    public Ui() {
        this.name = "Remy";
    }

    /**
     * Prints welcome message when program is started
     */
    public String showWelcome(TaskList tasks, Ui ui, Storage storage) {
        String recentTasks = new ListCommand(2).execute(tasks, ui, storage);
        return name + " is here.\n" + recentTasks + "\nWhat can I do for you?";
    }

    /**
     * Displays message to user when a task is deleted.
     * The message includes the status of deleted task and the remaining tasks in the TaskList
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param task the deleted task, used to display its task status
     */
    public String showDeleted(TaskList tasks, Task task) {
        return "Noted. I've removed this task.\n\t" + task.getStatus()
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Displays message to user when a task is added.
     * The message includes the status of added task and the total number of tasks in the TaskList
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param taskInd the added task, used to display its task status
     */
    public String showAdded(TaskList tasks, int taskInd) {
        return "Got it. I've added this task:\n\t" + tasks.getTaskStatus(taskInd)
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Displays message to user when a task is marked as done.
     * The message includes the status of task
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param taskInd the task marked as done, used to display its task status
     */
    public String showMark(TaskList tasks, int taskInd) {
        return "Nice, I've marked this task as done:\n\t" + tasks.getTaskStatus(taskInd);
    }

    /**
     * Displays message to user when a task is unmarked as done.
     * The message includes the status of task
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param taskInd the task unmarked as done, used to display its task status
     */
    public String showUnmark(TaskList tasks, int taskInd) {
        return "OK! I've marked this task as not done yet:\n\t" + tasks.getTaskStatus(taskInd) + "\nKeep it up!";
    }

    /**
     * Prints farewell message when program is exiting
     */
    public String showBye() {
        return "Bye! Hope to see you soon, your Majesty.";
    }

    /**
     * Displays a formatted list of tasks to the user based on the listing type.
     * If the list is empty, an appropriate "no tasks" message is shown instead of task entries.
     *
     * @param list the list of task descriptions to display
     * @param listingType an integer indicating the type of listing:
     *                    0 for all tasks, 1 for tasks on a specific date,
     *                    2 for recent tasks, 3 for tasks matching a keyword
     */
    public String showListing(List<String> list, int listingType) {
        StringBuilder response;
        if (listingType == 0) {
            if (list.isEmpty()) {
                response = new StringBuilder("You have no tasks in the list at this moment.\n");
            } else {
                response = new StringBuilder("Here are the tasks in the list:\n");
            }
        } else if (listingType == 1) {
            if (list.isEmpty()) {
                response = new StringBuilder("You have no tasks in the list at this specified date.\n");
            } else {
                response = new StringBuilder("Here are the tasks on the specified date:\n");
            }
        } else if (listingType == 2) {
            if (list.isEmpty()) {
                response = new StringBuilder("You have no tasks in the list on recent days\n");
            } else {
                response = new StringBuilder("Here are your recent tasks:\n");
            }
        } else if (listingType == 3) {
            if (list.isEmpty()) {
                response = new StringBuilder("You have no tasks in the list at this keyword.\n");
            } else {
                response = new StringBuilder("Here are the matching tasks in your lists:\n");
            }
        } else {
            assert false : "Unreachable kind: listing type will not have a value other than 0, 1 and 2.";
            response = new StringBuilder();
        }
        for (int i = 0; i < list.size(); i++) {
            int ind = i + 1;
            response.append("\t").append(ind).append(".").append(list.get(i)).append("\n");
        }
        return response.toString();
    }
}
