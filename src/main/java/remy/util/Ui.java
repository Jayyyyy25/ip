package remy.util;

import java.util.List;
import java.util.Scanner;

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
    public String showWelcome() {
        return "Hello! I'm " + name + ".\n" + "What can I do for you?";
    }

    /**
     * Prints error message when failed to fetch data from hard disk
     */
    public String showLoadingError() {
        return "Failed to fetch hard disk record";
    }

    /**
     * Fetches user's input
     *
     * @return User input
     */
    public String readComment() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints error message
     */
    public String showError(String errMessage) {
        return errMessage;
    }

    /**
     * Displays message to user when a task is deleted.
     * The message includes the status of deleted task and the remaining tasks in the TaskList
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param task the deleted task, used to display its task status
     */
    public String showDeleted(TaskList tasks, Task task) {
        return "Noted. I've removed this remy.task.\n\t" + task.getStatus()
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
        return "Got it. I've added this remy.task:\n\t" + tasks.getTaskStatus(taskInd)
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
        return "Nice, I've marked this remy.task as done:\n\t" + tasks.getTaskStatus(taskInd);
    }

    /**
     * Displays message to user when a task is unmarked as done.
     * The message includes the status of task
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param taskInd the task unmarked as done, used to display its task status
     */
    public String showUnmark(TaskList tasks, int taskInd) {
        return "OK! I've marked this remy.task as not done yet:\n\t" + tasks.getTaskStatus(taskInd);
    }

    /**
     * Prints farewell message when program is exiting
     */
    public String showBye() {
        return "Bye! Hope to see you soon!";
    }

    /**
     * Displays a formatted list of tasks to the user based on the listing type.
     * If the list is empty, an appropriate "no tasks" message is shown instead of task entries.
     *
     * @param list the list of task descriptions to display
     * @param listingType an integer indicating the type of listing:
     *                    0 for all tasks, 1 for tasks on a specific date, 2 for tasks matching a keyword
     */
    public String showListing(List<String> list, int listingType) {
        StringBuilder response;
        if (listingType == 0) {
            if (list.isEmpty()) {
                response = new StringBuilder("You have no tasks in the list at this moment.\n");
            } else {
                response = new StringBuilder("Here are the tasks in the list:\n");
            }

            for (int i = 0; i < list.size(); i++) {
                int ind = i + 1;
                response.append("\t").append(ind).append(".").append(list.get(i)).append("\n");
            }
        } else if (listingType == 1) {
            if (list.isEmpty()) {
                response = new StringBuilder("You have no tasks in the list at this specified date.\n");
            } else {
                response = new StringBuilder("Here are the tasks on the specified date:\n");
            }

            for (int i = 0; i < list.size(); i++) {
                int ind = i + 1;
                response.append("\t").append(ind).append(".").append(list.get(i)).append("\n");
            }
        } else if (listingType == 2) {
            if (list.isEmpty()) {
                response = new StringBuilder("You have no tasks in the list at this keyword.\n");
            } else {
                response = new StringBuilder("Here are the matching tasks in your lists:\n");
            }

            for (int i = 0; i < list.size(); i++) {
                int ind = i + 1;
                response.append("\t").append(ind).append(".").append(list.get(i)).append("\n");
            }
        } else {
            response = new StringBuilder();
        }
        return response.toString();
    }
}
