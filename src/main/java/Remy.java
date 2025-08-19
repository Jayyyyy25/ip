import java.util.Scanner;
import java.util.ArrayList;

public class Remy {
    private static String name = "Remy";
    private static String divider = "----------------------------------------";
    private static String logo =
            " ____  _____ __  __ __     __\n" +
            "|  _ \\| ____|  \\/  |\\ \\   / /\n" +
            "| |_) |  _| | |\\/| | \\ \\ / / \n" +
            "|  _ <| |___| |  | |  \\ V /  \n" +
            "|_| \\_\\_____|_|  |_|   |_|   \n";
    private static boolean running = true;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        greetUser();

        while (running) {
            System.out.println(divider);
            String userInput = scanner.nextLine();
            System.out.println(divider);

            // Split the inputs into command and arguments for easier case handling
            String[] parts = userInput.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";
            respond(command, argument);
        }
    }

    private static void greetUser() {
        System.out.println(logo);
        System.out.println("\t\t\tHello! I'm " + name + ".\n" + "\t\t\tWhat can I do for you?");
    }

    private static void respond(String command, String argument) {
        switch (command) {
            case "bye":
                running = false;
                exitBot();
                break;
            case "list":
                listing();
                break;
            case "mark":
                markAsDone(Integer.parseInt(argument) - 1);
                break;
            case "unmark":
                markAsUndone(Integer.parseInt(argument) - 1);
                break;
            default:
                add(command + argument);
                break;
        }
    }

    private static void exitBot() {
        System.out.println("\t\t\tBye. Hope to see you again soon!");
    }

    private static void listing() {
        // Headings for list
        if (tasks.isEmpty()) {
            System.out.println("\t\t\tYou have no tasks in the list at this moment.");
        } else {
            System.out.println("\t\t\tHere are the tasks in the list.");
        }

        for (int i = 0; i < tasks.size(); i++) {
            int ind = i + 1;
            System.out.println("\t\t\t" + ind + "." + tasks.get(i).getStatus());
        }
    }

    private static void add(String title) {
        tasks.add(new Task(title));
        System.out.println("\t\t\tadded: " + title);
    }

    private static void markAsDone(int taskInd) {
        tasks.get(taskInd).markAsDone();
        System.out.println("\t\t\tNice! I've marked this task as done:");
        System.out.println("\t\t\t" + tasks.get(taskInd).getStatus());
    }

    private static void markAsUndone(int taskInd) {
        tasks.get(taskInd).markAsUndone();
        System.out.println("\t\t\tOk, I've marked this task as not done yet:");
        System.out.println("\t\t\t" + tasks.get(taskInd).getStatus());
    }
}
