package remy.util;

import remy.task.Task;
import remy.task.TaskList;

import java.util.Scanner;

/**
 * Handles all user interactions such as displaying messages,
 * reading input, and showing feedback when tasks are modified.
 */
public class Ui {
    private String name;
    private static Scanner scanner = new Scanner(System.in);

    public Ui() {
        this.name = "Remy";
    }

    /**
     * Prints welcome message when program is started
     */
    public void showWelcome() {
        System.out.println("\t\t\tHello! I'm " + name + ".\n" + "\t\t\tWhat can I do for you?");
    }

    /**
     * Prints error message when failed to fetch data from hard disk
     */
    public void showLoadingError() {
        System.out.println("\t\t\t(Failed to fetch hard disk record");
    }

    /**
     * Prints divider line
     */
    public void showLine() {
        String divider = "----------------------------------------";
        System.out.println(divider);
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
    public void showError(String errMessage) {
        System.out.println("\t\t\t" + errMessage);
    }

    /**
     * Displays message to user when a task is deleted.
     * The message includes the status of deleted task and the remaining tasks in the TaskList
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param task the deleted task, used to display its task status
     */
    public void showDeleted(TaskList tasks, Task task) {
        System.out.println("\t\t\tNoted. I've removed this remy.task.");
        System.out.println("\t\t\t\t" + task.getStatus());
        System.out.println("\t\t\tNow you have " + tasks.getSize() + " tasks in the list.");
    }

    /**
     * Displays message to user when a task is added.
     * The message includes the status of added task and the total number of tasks in the TaskList
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param taskInd the added task, used to display its task status
     */
    public void showAdded(TaskList tasks, int taskInd) {
        System.out.println("\t\t\tGot it. I've added this remy.task:");
        System.out.println("\t\t\t\t" + tasks.getTaskStatus(taskInd));
        System.out.println("\t\t\tNow you have " + tasks.getSize() + " tasks in the list.");
    }

    /**
     * Displays message to user when a task is marked as done.
     * The message includes the status of task
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param taskInd the task marked as done, used to display its task status
     */
    public void showMark(TaskList tasks, int taskInd) {
        System.out.println("\t\t\tNice, I've marked this remy.task as done:");
        System.out.println("\t\t\t" + tasks.getTaskStatus(taskInd));
    }

    /**
     * Displays message to user when a task is unmarked as done.
     * The message includes the status of task
     *
     * @param tasks the current TaskList, used to get the updated task count
     * @param taskInd the task unmarked as done, used to display its task status
     */
    public void showUnmark(TaskList tasks, int taskInd) {
        System.out.println("\t\t\tOK! I've marked this remy.task as not done yet:");
        System.out.println("\t\t\t" + tasks.getTaskStatus(taskInd));
    }

    /**
     * Prints farewell message when program is exiting
     */
    public void showBye() {
        System.out.println("\t\t\tBye! Hope to see you soon!");
    }
}
