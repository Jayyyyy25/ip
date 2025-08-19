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
    private static ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        greetUser();

        while (running) {
            System.out.println(divider);
            String userInput = scanner.nextLine();
            System.out.println(divider);
            respond(userInput);
        }
    }

    private static void greetUser() {
        System.out.println(logo);
        System.out.println("\t\t\tHello! I'm " + name + ".\n" + "\t\t\tWhat can I do for you?");
    }

    private static void respond(String input) {
        switch (input) {
            case "bye":
                running = false;
                exitBot();
                break;
            case "list":
                listing();
                break;
            default:
                add(input);
                break;
        }
    }

    private static void exitBot() {
        System.out.println("\t\t\tBye. Hope to see you again soon!");
    }

    private static void listing() {
        for (int i = 0; i < tasks.size(); i++) {
            int ind = i + 1;
            System.out.println("\t\t\t" + ind + ": " + tasks.get(i));
        }
    }

    private static void add(String task) {
        tasks.add(task);
        System.out.println("\t\t\tadded: " + task);
    }
}
