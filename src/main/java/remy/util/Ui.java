package remy.util;

import remy.task.Task;
import remy.task.TaskList;

import java.util.Scanner;

public class Ui {
    private String name;
    private static Scanner scanner = new Scanner(System.in);

    public Ui() {
        this.name = "remy.ui.Remy";
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
        System.out.println("\t\t\tError: " + errMessage);
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
}
