package remy.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;

import remy.command.AddCommand;
import remy.command.Command;
import remy.command.DeleteCommand;
import remy.command.EditCommand;
import remy.command.ExitCommand;
import remy.command.FindCommand;
import remy.command.ListCommand;
import remy.exception.InvalidArgumentException;
import remy.exception.InvalidCommandException;
import remy.exception.InvalidDateFormatException;
import remy.exception.RemyException;
import remy.task.DeadlineTask;
import remy.task.EventTask;
import remy.task.Task;
import remy.task.TodoTask;

/**
 * Handles parsing the user input into executable {@link Command} objects,
 * parsing stored data back into {@link Task} objects,
 * and interpreting data/time strings into {@link LocalDateTime}
 */
public class Parser {

    // Supported date/time objects for parsing the user inputs
    private static final List<DateTimeFormatter> DATE_FORMATS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
            DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("HH:mm yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("HHmm yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("HHmm yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("HHmm dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("HHmm dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy")
    );

    // Internal enumerations of valid commands
    private enum Commands {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND
    }

    /**
     * Parses raw user input into executable {@link Command}
     *
     * @param userInput raw input entered by user
     * @return an executable {@link Command}
     * @throws RemyException if the command is invalid or arguments are missing
     */
    public static Command parseCommand(String userInput) throws RemyException {
        // Parse user input into command and argument
        String[] commandSplit = userInput.split(" ", 2);
        String command = commandSplit[0].trim();
        String argument = commandSplit.length > 1 ? commandSplit[1].trim() : "";

        try {
            Commands cmd = Commands.valueOf(command.toUpperCase());
            switch (cmd) {
            case BYE:
                return new ExitCommand();
            case LIST:
                return parseListCommand(argument);
            case TODO:
                return parseTodoTask(argument);
            case DEADLINE:
                return parseDeadlineTask(argument);
            case EVENT:
                return parseEventTask(argument);
            case MARK:
                if (!argument.isEmpty() && canParseInt(argument)) {
                    return new EditCommand(1, Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Provide a valid index to mark as done, PLEASE~");
                }
            case UNMARK:
                if (!argument.isEmpty() && canParseInt(argument)) {
                    return new EditCommand(0, Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Provide a valid index to mark as undone, PLEASE~");
                }
            case DELETE:
                if (!argument.isEmpty() && canParseInt(argument)) {
                    return new DeleteCommand(Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Provide a valid index to remove the task, PLEASE~");
                }
            case FIND:
                if (!argument.isEmpty()) {
                    return new FindCommand(argument.toLowerCase());
                } else {
                    throw new InvalidArgumentException("Provide a keyword to search, PLEASE~");
                }
            default:
                throw new InvalidCommandException(String.format(
                        "'%s' command not found", command));
            }
        } catch (RemyException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidCommandException(String.format("'%s' command not found", command));
        }
    }

    /**
     * Parses a list command arguments into an executable command
     *
     * @param argument Argument given by the user
     * @return A {@code ListCommand}
     */
    private static Command parseListCommand(String argument) throws RemyException {
        if (argument.isEmpty()) {
            return new ListCommand(0);
        } else if (argument.contains("/on")) {
            String[] argumentSplit = argument.split("/on", 2);
            String dateStr = argumentSplit[1].trim();
            if (dateStr.isEmpty()) {
                throw new InvalidArgumentException(
                        "Use /on to specify a date for specific date listing, PLEASE~");
            }
            LocalDate date;
            try {
                date = Parser.parseDateTime(dateStr).toLocalDate();
            } catch (Exception e) {
                throw new InvalidDateFormatException("I don't like this date format.\n"
                        + "Use a valid date format (DD/MM/YYYY HH:MM) to specify date, PLEASE~");
            }
            return new ListCommand(1, date);
        } else if (argument.contains("/recent")) {
            return new ListCommand(2);
        } else {
            throw new InvalidCommandException(String.format(
                    "'list %s' command not found", argument));
        }
    }

    /**
     * Parses a TodoTask command argument into an executable command
     *
     * @param argument Arguments such as title
     * @return A {@code AddCommand}
     */
    private static Command parseTodoTask(String argument) throws RemyException {
        if (!argument.isEmpty()) {
            return new AddCommand(argument);
        } else {
            throw new InvalidArgumentException("Newly added task could not have blank title.");
        }
    }

    /**
     * Parses a DeadlineTask command argument into an executable command
     *
     * @param argument Arguments such as title and deadline
     * @return A {@code AddCommand}
     */
    private static Command parseDeadlineTask(String argument) throws RemyException {
        if (argument.isEmpty()) {
            throw new InvalidArgumentException("Newly added task could not have blank title");
        }

        if (!argument.contains("/by")) {
            throw new InvalidArgumentException("Use /by to specify a deadline for deadline task, PLEASE~");
        }

        String[] titleSplit = argument.split("/by", 2);
        String title = titleSplit[0].trim();
        if (title.isEmpty()) {
            throw new InvalidArgumentException("Newly added task could not have blank title.");
        }
        String ddlStr = titleSplit[1].trim();
        if (ddlStr.isEmpty()) {
            throw new InvalidArgumentException(
                    "Use /by to specify a deadline for deadline task, PLEASE~");
        }
        LocalDateTime ddl;
        try {
            ddl = Parser.parseDateTime(ddlStr);
        } catch (Exception e) {
            throw new InvalidDateFormatException("I don't like this date format."
                    + "\nUse a valid date format (DD/MM/YYYY HH:MM) to specify deadline, PLEASE~");
        }
        return new AddCommand(title, ddl);
    }

    /**
     * Parses a EventTask command argument into an executable command
     *
     * @param argument Arguments such as title, event start date, event end date
     * @return A {@code AddCommand}
     */
    private static Command parseEventTask(String argument) throws RemyException {
        if (argument.isEmpty()) {
            throw new InvalidArgumentException("Newly added task could not have blank description.");
        }

        if (!argument.contains("/from") || !argument.contains("/to")) {
            throw new InvalidArgumentException(
                    "Use /from and /to to specify a date / time for event task, PLEASE~");
        }

        String[] fromSplit = argument.split("/from", 2);
        String title = fromSplit[0].trim();
        if (title.isEmpty()) {
            throw new InvalidArgumentException("Newly added task could not have blank title.");
        }
        String[] toSplit = fromSplit[1].split("/to", 2);
        String fromStr = toSplit[0].trim();
        String toStr = toSplit[1].trim();
        if (fromStr.isEmpty() || toStr.isEmpty()) {
            throw new InvalidArgumentException(
                    "Use /from and /to to specify a date / time for event task, PLEASE~");
        }
        LocalDateTime from;
        LocalDateTime to;
        try {
            from = Parser.parseDateTime(fromStr);
            to = Parser.parseDateTime(toStr);
        } catch (Exception e) {
            throw new InvalidDateFormatException(e.getMessage()
                    + "\nUse a valid date format (DD/MM/YYYY HH:MM) to specify time, PLEASE~");
        }
        return new AddCommand(title, from, to);
    }

    /**
     * Parses a line of stored task data into a {@link Task} object
     *
     * @param data task data stored in text
     * @return a {@link Task} object
     * @throws RemyException if the data is invalid or improperly formatted
     */
    public static Task parseTask(String data) throws RemyException {
        try {
            String[] taskString = data.split("\\|", 3);
            String taskType = taskString[0].trim();
            String isDoneStr = taskString[1].trim();
            int isDone = Integer.parseInt(isDoneStr);
            String taskInfo = taskString[2].trim();
            String title;

            switch (taskType) {
            case "T":
                title = taskInfo;
                return new TodoTask(title, isDone != 0);
            case "D":
                String[] titleSplit = taskInfo.split("\\|", 2);
                title = titleSplit[0].trim();
                String ddl = titleSplit[1].trim();
                return new DeadlineTask(title, Parser.parseDateTime(ddl), isDone != 0);
            case "E":
                String[] taskInfoSplit = taskInfo.split("\\|", 3);
                title = taskInfoSplit[0].trim();
                String from = taskInfoSplit[1].trim();
                String to = taskInfoSplit[2].trim();
                return new EventTask(title, Parser.parseDateTime(from), Parser.parseDateTime(to), isDone != 0);
            default:
                throw new InvalidArgumentException("Invalid string input");
            }
        } catch (Exception e) {
            throw new RemyException("Invalid data parsed from hard disk");
        }
    }

    /**
     * Parses a user input date/time string into a {@link LocalDateTime} object
     *
     * @param input raw date/time string entered by user
     * @return a {@link LocalDateTime} object
     * @throws IllegalArgumentException if date/time string is not formatted well
     */
    public static LocalDateTime parseDateTime(String input) throws InvalidArgumentException {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                if (formatter.toString().contains("H")) {
                    return LocalDateTime.parse(input, formatter);
                } else {
                    return LocalDate.parse(input, formatter.withResolverStyle(ResolverStyle.STRICT)).atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {
                continue;
            }
        }
        throw new IllegalArgumentException("Unparseable date: " + input);
    }

    private static boolean canParseInt(String ind) {
        try {
            Integer.parseInt(ind);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
