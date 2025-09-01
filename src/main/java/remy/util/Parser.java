package remy.util;

import remy.command.*;
import remy.exception.InvalidArgumentException;
import remy.exception.InvalidCommandException;
import remy.exception.InvalidDateFormatException;
import remy.exception.RemyException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class Parser {
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
    private enum Commands {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE
    }

    public static Command parseCommand(String userInput) throws RemyException {
        String[] commandSplit = userInput.split(" ", 2);
        String command = commandSplit[0];
        String argument = commandSplit.length > 1 ? commandSplit[1] : "";
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
                        throw new InvalidDateFormatException(e.getMessage() +
                                "\n\t\t\tPlease use a valid date format (DD/MM/YYYY HH:MM) to specify date");
                    }
                    return new ListCommand(date);
                } else {
                    throw new InvalidCommandException(String.format("Invalid comment error: 'list %s' remy.command not found", argument));
                }
            case TODO:
                if (!argument.isEmpty()) {
                    return new AddCommand(argument);
                } else {
                    throw new InvalidArgumentException("Newly added remy.task could not have blank title.");
                }
            case DEADLINE:
                if (!argument.isEmpty()) {
                    if (argument.contains("/by")) {
                        String[] titleSplit = argument.split("/by", 2);
                        String title = titleSplit[0].trim();
                        if (title.isEmpty()) {
                            throw new InvalidArgumentException("Newly added remy.task could not have blank title.");
                        }
                        String ddlStr = titleSplit[1].trim();
                        if (ddlStr.isEmpty()) {
                            throw new InvalidArgumentException("Please use /by to specify a deadline for deadline remy.task.");
                        }
                        LocalDateTime ddl;
                        try {
                            ddl = Parser.parseDateTime(ddlStr);
                        } catch (Exception e) {
                            throw new InvalidDateFormatException(e.getMessage() +
                                    "\n\t\t\tPlease use a valid date format (DD/MM/YYYY HH:MM) to specify deadline");
                        }
                        return new AddCommand(title, ddl);
                    }
                } else {
                    throw new InvalidArgumentException("Newly added remy.task could not have blank title.");
                }
            case EVENT:
                if (argument.isEmpty()) {
                    throw new InvalidArgumentException("Newly added remy.task could not have blank description.");
                } else if (argument.contains("/from") && argument.contains("/to")) {
                    String[] fromSplit = argument.split("/from", 2);
                    String title = fromSplit[0].trim();
                    if (title.isEmpty()) {
                        throw new InvalidArgumentException("Newly added remy.task could not have blank title.");
                    }
                    String[] toSplit = fromSplit[1].split("/to", 2);
                    String fromStr = toSplit[0].trim();
                    String toStr = toSplit[1].trim();
                    if (fromStr.isEmpty() || toStr.isEmpty()) {
                        throw new InvalidArgumentException("Please use /from and /to to specify a date / time for event remy.task.");
                    }
                    LocalDateTime from;
                    LocalDateTime to;
                    try {
                        from = Parser.parseDateTime(fromStr);
                        to = Parser.parseDateTime(toStr);
                    } catch (Exception e) {
                        throw new InvalidDateFormatException(e.getMessage() +
                                "\n\t\t\tPlease use a valid date format (DD/MM/YYYY HH:MM) to specify time");
                    }
                    return new AddCommand(title, from, to);
                } else {
                    throw new InvalidArgumentException("Please use /from and /to to specify a date / time for event remy.task.");
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
            default:
                throw new InvalidCommandException(String.format("Invalid remy.command error: '%s' remy.command not found", command));
            }
        } catch (Exception e) {
            throw new RemyException(e.getMessage());
        }
    }

    public static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                if (formatter.toString().contains("H")) {
                    return LocalDateTime.parse(input, formatter);
                } else {
                    return LocalDate.parse(input, formatter).atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Unparseable date: " + input);
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
}