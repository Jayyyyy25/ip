import java.io.IOException;
import java.util.List;
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
    private enum Command {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE
    }
    private static Storage dataStorage = new Storage("./data/remy.txt");

    public static void main(String[] args) throws InvalidArgumentException {
        List<String> savedTasks = dataStorage.load();
        for (String line : savedTasks) {
            try {
                tasks.add(Task.parseTask(line));
            } catch (InvalidArgumentException e) {
                throw new InvalidArgumentException("Line with invalid task type found");
            }
        }

        greetUser();

        while (running) {
            System.out.println(divider);
            String userInput = scanner.nextLine().trim();
            System.out.println(divider);

            // Split the inputs into command and arguments for easier case handling
            String[] parts = userInput.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";
            try {
                respond(command, argument);
            } catch (InvalidArgumentException exp) {
                System.out.println("\t\t\tInvalid Arguments: " + exp.getMessage());
            } catch (InvalidCommandException exp) {
                System.out.println("\t\t\tInvalid Command: " + exp.getMessage());
            } catch (RemyException exp) {
                System.out.println("\t\t\tError:" + exp.getMessage());
            }
        }
    }

    private static void greetUser() {
        // System.out.println(logo);
        System.out.println("\t\t\tHello! I'm " + name + ".\n" + "\t\t\tWhat can I do for you?");
    }

    // Method to check whether valid index is provided
    private static boolean canParseInt(String ind) {
        try {
            Integer.parseInt(ind);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void respond(String command, String argument) throws RemyException {
        try {
            Command cmd = Command.valueOf(command.toUpperCase());
            switch (cmd) {
            case BYE:
                running = false;
                exitBot();
                break;
            case LIST:
                listing();
                break;
            case MARK:
                if (!argument.isEmpty() && canParseInt(argument) && Integer.parseInt(argument) <= tasks.size()) {
                    markAsDone(Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Please provide a valid index to mark as done.");
                }
                break;
            case UNMARK:
                if (!argument.isEmpty() && canParseInt(argument) && Integer.parseInt(argument) <= tasks.size()) {
                    markAsUndone(Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Please provide a valid index to mark as undone.");
                }
                break;
            case TODO:
                if (!argument.isEmpty()) {
                    add(todo(argument));
                } else {
                    throw new InvalidArgumentException("Newly added task could not have blank title.");
                }
                break;
            case DEADLINE:
                if (argument.isEmpty()) {
                    throw new InvalidArgumentException("Newly added task could not have blank description.");
                } else if (argument.contains("/by")) {
                    String[] parts = argument.split("/by", 2);
                    String title = parts[0].trim();
                    if (title.isEmpty()) {
                        throw new InvalidArgumentException("Newly added task could not have blank title.");
                    }
                    String ddl = parts[1].trim();
                    if (ddl.isEmpty()) {
                        throw new InvalidArgumentException("Please use /by to specify a deadline for deadline task.");
                    }
                    add(deadline(title, ddl));
                } else {
                    throw new InvalidArgumentException("Please use /by to specify a deadline for deadline task.");
                }
                break;
            case EVENT:
                if (argument.isEmpty()) {
                    throw new InvalidArgumentException("Newly added task could not have blank description.");
                } else if (argument.contains("/from") && argument.contains("/to")) {
                    String[] fromSplit = argument.split("/from", 2);
                    String title = fromSplit[0].trim();
                    if (title.isEmpty()) {
                        throw new InvalidArgumentException("Newly added task could not have blank title.");
                    }
                    String[] toSplit = fromSplit[1].split("/to", 2);
                    String from = toSplit[0].trim();
                    String to = toSplit[1].trim();
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new InvalidArgumentException("Please use /from and /to to specify a date / time for event task.");
                    }
                    add(event(title, from, to));
                } else {
                    throw new InvalidArgumentException("Please use /from and /to to specify a date / time for event task.");
                }
                break;
            case DELETE:
                if (!argument.isEmpty() && canParseInt(argument) && Integer.parseInt(argument) <= tasks.size()) {
                    delete(Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Please provide a valid index to remove the task.");
                }
                break;
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("'" + command + "' " + "command not found.");
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
        try {
            dataStorage.appendLine(task.toString());
        } catch (IOException e) {
            System.out.println("\t\t\tError adding new task: " + e.getMessage());
        }
        System.out.println("\t\t\tGot it. I've added this task:");
        System.out.println("\t\t\t\t" + task.getStatus());
        System.out.println("\t\t\tNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void markAsDone(int taskInd) {
        tasks.get(taskInd).markAsDone();
        try {
            dataStorage.updateLine(taskInd, tasks.get(taskInd).toString());
        } catch (IOException e) {
            System.out.println("\t\t\tError updating task completeness: " + e.getMessage());
        }
        System.out.println("\t\t\tNice! I've marked this task as done:");
        System.out.println("\t\t\t" + tasks.get(taskInd).getStatus());
    }

    private static void markAsUndone(int taskInd) {
        tasks.get(taskInd).markAsUndone();
        try {
            dataStorage.updateLine(taskInd, tasks.get(taskInd).toString());
        } catch (IOException e) {
            System.out.println("\t\t\tError updating task completeness: " + e.getMessage());
        }
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

    private static void delete(int taskInd) {
        Task task = tasks.get(taskInd);
        tasks.remove(taskInd);
        try {
            dataStorage.deleteLine(taskInd);
        } catch (IOException e) {
            System.out.println("\t\t\tError deleting task: " + e.getMessage());
        }
        System.out.println("\t\t\tNoted. I've removed this task.");
        System.out.println("\t\t\t\t" + task.getStatus());
        System.out.println("\t\t\tNow you have " + tasks.size() + " tasks in the list.");
    }
}
