import java.util.Scanner;

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

    public static void main(String[] args) {
        greetUser();

        while (running) {
            System.out.println(divider);
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                running = false;
                System.out.println(divider);
                exitBox();
            } else {
                System.out.println(divider);
                respond(userInput);
            }
        }
    }

    private static void greetUser() {
        System.out.println(logo);
        System.out.println("\t\t\tHello! I'm " + name + ".\n" + "\t\t\tWhat can I do for you?");
    }

    private static void exitBox() {
        System.out.println("\t\t\tBye. Hope to see you soon!");
    }

    private static void respond(String input) {
        switch (input) {
            case "list":
                listing();
                break;
            default:
                System.out.println("\t\t\t" + input);
                break;
        }
    }

    private static void listing() {
        System.out.println("\t\t\tlist");
    }
}
