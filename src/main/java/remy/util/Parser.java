package remy.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        String command;
        String argument;
        try {
            String[] commandSplit = userInput.split(" ", 2);
            command = commandSplit[0];
            argument = commandSplit.length > 1 ? commandSplit[1] : "";
        } catch (Exception e) {
            throw new InvalidCommandException("Invalid command: command not found");
        }

        try {
            Commands cmd = Commands.valueOf(command.toUpperCase());
            switch (cmd) {
            case BYE:
                return new ExitCommand();
            case LIST:
                if (argument.isEmpty()) {
                    return new ListCommand();
                } else if (argument.contains("/on")) {
                    String[] argumentSplit = argument.split("/on", 2);
                    String dateStr = argumentSplit[1].trim();
                    if (dateStr.isEmpty()) {
                        throw new InvalidArgumentException(
                                "Please use /by to specify a date for specific date listing.");
                    }
                    LocalDate date;
                    try {
                        date = Parser.parseDateTime(dateStr).toLocalDate();
                    } catch (Exception e) {
                        throw new InvalidDateFormatException(e.getMessage()
                                + "\n\t\t\tPlease use a valid date format (DD/MM/YYYY HH:MM) to specify date");
                    }
                    return new ListCommand(date);
                } else {
                    throw new InvalidCommandException(String.format(
                            "Invalid comment error: 'list %s' command not found", argument));
                }
            case TODO:
                if (!argument.isEmpty()) {
                    return new AddCommand(argument);
                } else {
                    throw new InvalidArgumentException("Newly added task could not have blank title.");
                }
            case DEADLINE:
                if (!argument.isEmpty()) {
                    if (argument.contains("/by")) {
                        String[] titleSplit = argument.split("/by", 2);
                        String title = titleSplit[0].trim();
                        if (title.isEmpty()) {
                            throw new InvalidArgumentException("Newly added task could not have blank title.");
                        }
                        String ddlStr = titleSplit[1].trim();
                        if (ddlStr.isEmpty()) {
                            throw new InvalidArgumentException(
                                    "Please use /by to specify a deadline for deadline task.");
                        }
                        LocalDateTime ddl;
                        try {
                            ddl = Parser.parseDateTime(ddlStr);
                        } catch (Exception e) {
                            throw new InvalidDateFormatException(e.getMessage()
                                    + "\nPlease use a valid date format (DD/MM/YYYY HH:MM) to specify deadline");
                        }
                        return new AddCommand(title, ddl);
                    }
                    throw new InvalidArgumentException("Please use /by to specify a deadline for deadline task");
                } else {
                    throw new InvalidArgumentException("Newly added task could not have blank title.");
                }
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
                    String fromStr = toSplit[0].trim();
                    String toStr = toSplit[1].trim();
                    if (fromStr.isEmpty() || toStr.isEmpty()) {
                        throw new InvalidArgumentException(
                                "Please use /from and /to to specify a date / time for event remy.task.");
                    }
                    LocalDateTime from;
                    LocalDateTime to;
                    try {
                        from = Parser.parseDateTime(fromStr);
                        to = Parser.parseDateTime(toStr);
                    } catch (Exception e) {
                        throw new InvalidDateFormatException(e.getMessage()
                                + "\nPlease use a valid date format (DD/MM/YYYY HH:MM) to specify time");
                    }
                    return new AddCommand(title, from, to);
                } else {
                    throw new InvalidArgumentException(
                            "Please use /from and /to to specify a date / time for event remy.task.");
                }
            case MARK:
                if (!argument.isEmpty() && canParseInt(argument)) {
                    return new EditCommand(1, Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Please provide a valid index to mark as done.");
                }
            case UNMARK:
                if (!argument.isEmpty() && canParseInt(argument)) {
                    return new EditCommand(0, Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Please provide a valid index to mark as undone.");
                }
            case DELETE:
                if (!argument.isEmpty() && canParseInt(argument)) {
                    return new DeleteCommand(Integer.parseInt(argument) - 1);
                } else {
                    throw new InvalidArgumentException("Please provide a valid index to remove the remy.task.");
                }
            case FIND:
                if (!argument.isEmpty()) {
                    return new FindCommand(argument.toLowerCase());
                } else {
                    throw new InvalidArgumentException("Please provide a keyword to search");
                }
            default:
                assert false : "Invalid command error: '%s' command not found" + command;
                throw new InvalidCommandException(String.format(
                        "Invalid command error: '%s' command not found", command));
            }
        } catch (RemyException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidCommandException(String.format("Invalid command error: '%s' command not found", command));
        }
    }

    /**
     * Parses a line of stored task data into a {@link Task} object
     *
     * @param data task data stored in text
     * @return a {@link Task} object
     * @throws RemyException if the data is invalid or improperly formatted
     */
    public static Task parseTask(String data) throws RemyException {
        String taskType;
        String isDoneStr;
        String taskInfo;
        String title;
        int isDone;

        try {
            String[] taskTypeSplit = data.split("\\|", 2);
            taskType = taskTypeSplit[0].trim();
            String[] isDoneSplit = taskTypeSplit[1].split("\\|", 2);
            isDoneStr = isDoneSplit[0].trim();
            taskInfo = isDoneSplit[1].trim();
        } catch (Exception e) {
            throw new RemyException("Invalid data parsed from hard disk");
        }
        switch (taskType) {
        case "T":
            isDone = Integer.parseInt(isDoneStr);
            title = taskInfo;
            return new TodoTask(title, isDone != 0);
        case "D":
            isDone = Integer.parseInt(isDoneStr);
            String[] titleSplit = taskInfo.split("\\|", 2);
            title = titleSplit[0].trim();
            String ddl = titleSplit[1].trim();
            return new DeadlineTask(title, Parser.parseDateTime(ddl), isDone != 0);
        case "E":
            isDone = Integer.parseInt(isDoneStr);
            String[] fromSplit = taskInfo.split("\\|", 2);
            title = fromSplit[0].trim();
            String [] toSplit = fromSplit[1].split("\\|", 2);
            String from = toSplit[0].trim();
            String to = toSplit[1].trim();
            return new EventTask(title, Parser.parseDateTime(from), Parser.parseDateTime(to), isDone != 0);
        default:
            assert false : "Unreachable kind: this task type is invalid";
            throw new InvalidArgumentException("Invalid string input");
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
                    return LocalDate.parse(input, formatter).atStartOfDay();
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
