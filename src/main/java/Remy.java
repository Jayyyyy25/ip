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
            String userInput = scanner.nextLine().trim();
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
            case "todo":
                add(todo(argument));
                break;
            case "deadline":
                if (argument.contains("/by")) {
                    String[] parts = argument.split("/by", 2);
                    String title = parts[0].trim();
                    String ddl = parts[1].trim();
                    add(deadline(title, ddl));
                } else {
                    System.out.println("\t\t\tPlease use /by to specify a deadline for deadline task");
                }
                break;
            case "event":
                if (argument.contains("/from") && argument.contains("/to")) {
                    String[] fromSplit = argument.split("/from", 2);
                    String title = fromSplit[0].trim();
                    String[] toSplit = fromSplit[1].split("/to", 2);
                    String from = toSplit[0].trim();
                    String to = toSplit[1].trim();
                    add(event(title, from, to));
                } else {
                    System.out.println("\t\t\tPlease use /from and /to to specify a date / time for event task");
                }
                break;
            default:
                System.out.println("\t\t\tInvalid command");
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

    private static void add(Task task) {
        tasks.add(task);
        System.out.println("\t\t\tGot it. I've added this task:");
        System.out.println("\t\t\t\t" + task.getStatus());
        System.out.println("\t\t\tNow you have " + tasks.size() + " tasks in the list.");
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

    private static Task todo(String title) {
        return new TodoTask(title);
    }

    private static Task deadline(String title, String ddl) {
        return new DeadlineTask(title, ddl);
    }

    private static Task event(String title, String from, String to) {
        return new EventTask(title, from, to);
    }
}
