package remy.util;

import remy.task.Task;
import remy.task.TaskList;

import java.util.*;

public class Ui {
    private String name;
    private static Scanner scanner = new Scanner(System.in);

    public Ui() {
        this.name = "Remy";
    }

    public void showWelcome() {
        System.out.println("\t\t\tHello! I'm " + name + ".\n" + "\t\t\tWhat can I do for you?");
    }

    public void showLoadingError() {
        System.out.println("\t\t\t(Failed to fetch hard disk record");
    }

    public void showLine() {
        String divider = "----------------------------------------";
        System.out.println(divider);
    }

    public String readComment() {
        return scanner.nextLine().trim();
    }

    public void showError(String errMessage) {
        System.out.println("\t\t\t" + errMessage);
    }

    public void showDeleted(TaskList tasks, Task task) {
        System.out.println("\t\t\tNoted. I've removed this remy.task.");
        System.out.println("\t\t\t\t" + task.getStatus());
        System.out.println("\t\t\tNow you have " + tasks.getSize() + " tasks in the list.");
    }

    public void showAdded(TaskList tasks, int taskInd) {
        System.out.println("\t\t\tGot it. I've added this remy.task:");
        System.out.println("\t\t\t\t" + tasks.getTaskStatus(taskInd));
        System.out.println("\t\t\tNow you have " + tasks.getSize() + " tasks in the list.");
    }

    public void showMark(TaskList tasks, int taskInd) {
        System.out.println("\t\t\tNice, I've marked this remy.task as done:");
        System.out.println("\t\t\t" + tasks.getTaskStatus(taskInd));
    }

    public void showUnmark(TaskList tasks, int taskInd) {
        System.out.println("\t\t\tOK! I've marked this remy.task as not done yet:");
        System.out.println("\t\t\t" + tasks.getTaskStatus(taskInd));
    }

    public void showBye() {
        System.out.println("\t\t\tBye! Hope to see you soon!");
    }

    /**
     * Displays a formatted list of tasks to the user based on the listing type.
     * If the list is empty, an appropriate "no tasks" message is shown instead of task entries.
     *
     * @param list the list of task descriptions to display
     * @param listingType an integer indicating the type of listing:
     *                    0 for all tasks, 1 for tasks on a specific date, 2 for tasks matching a keyword
     */
    public void showListing(List<String> list, int listingType) {
        if (listingType == 0) {
            if (list.isEmpty()) {
                System.out.println("\t\t\tYou have no tasks in the list at this moment.");
            } else {
                System.out.println("\t\t\tHere are the tasks in the list:");
            }

            for (int i = 0; i < list.size(); i++) {
                int ind = i + 1;
                System.out.println("\t\t\t" + ind + "." + list.get(i));
            }
        } else if (listingType == 1) {
            if (list.isEmpty()) {
                System.out.println("\t\t\tYou have no tasks in the list at this specified date.");
            } else {
                System.out.println("\t\t\tHere are the tasks on the specified date:");
            }

            for (int i = 0; i < list.size(); i++) {
                int ind = i + 1;
                System.out.println("\t\t\t" + ind + "." + list.get(i));
            }
        } else if (listingType == 2) {
            if (list.isEmpty()) {
                System.out.println("\t\t\tYou have no tasks in the list at this keyword.");
            } else {
                System.out.println("\t\t\tHere are the matching tasks in your lists:");
            }

            for (int i = 0; i < list.size(); i++) {
                int ind = i + 1;
                System.out.println("\t\t\t" + ind + "." + list.get(i));
            }
        }
    }
}
