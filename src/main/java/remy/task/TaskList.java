package remy.task;

import remy.exception.InvalidArgumentException;
import remy.exception.RemyException;

import remy.util.Parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks. Provides methods to add, delete, mark/unmark tasks,
 * and list out the tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList from a list of strings (loaded from storage).
     *
     * @param lines the list of strings representing tasks
     * @throws RemyException if any line cannot be parsed into a valid Task
     */
    public TaskList(List<String> lines) throws RemyException {
        tasks = new ArrayList<>();
        for (String line : lines) {
            try {
                tasks.add(Parser.parseTask(line));
            } catch (InvalidArgumentException e) {
                throw new InvalidArgumentException("Line with invalid task type found");
            }
        }
    }

    /**
     * Prints the tasks in the list. If a date is specified, only tasks
     * that are covered by that date are printed.
     *
     * @param specifiedDate date to filter tasks by; if null, all tasks are printed
     */
    public void getListing(LocalDate specifiedDate) {
        if (specifiedDate == null) {
            if (tasks.isEmpty()) {
                System.out.println("\t\t\tYou have no tasks in the list at this moment.");
            } else {
                System.out.println("\t\t\tHere are the tasks in the list.");
            }

            for (int i = 0; i < tasks.size(); i++) {
                int ind = i + 1;
                System.out.println("\t\t\t" + ind + "." + tasks.get(i).getStatus());
            }
        } else {
            List<Task> taskOnDate = tasks.stream()
                    .filter(task -> task.isCovered(specifiedDate))
                    .toList();

            if (taskOnDate.isEmpty()) {
                System.out.println("\t\t\tYou have no tasks in the list at this specified date.");
            } else {
                System.out.println("\t\t\tHere are the tasks on the specified date.");
            }

            for (int i = 0; i < taskOnDate.size(); i++) {
                int ind = i + 1;
                System.out.println("\t\t\t" + ind + "." + taskOnDate.get(i).getStatus());
            }
        }
    }

    /**
     * Returns the number of tasks in the list
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Deletes  a task at a specified index.
     *
     * @param ind the index of the task to delete
     * @return the deleted task
     */
    public Task deleteItem(int ind) {
        Task task  = tasks.get(ind);
        tasks.remove(task);
        return task;
    }

    /**
     * Returns the status of the task at the specified index
     *
     * @param ind index of the task
     */
    public String getTaskStatus(int ind) {
        return tasks.get(ind).getStatus();
    }

    /**
     * Adds a task into the list
     *
     * @param task task to be added
     */
    public int addItem(Task task) {
        tasks.add(task);
        return tasks.indexOf(task);
    }

    /**
     * Marks a task as done at a specified index
     *
     * @param taskInd index of the task to be marked
     */
    public void markAsDone(int taskInd) {
        tasks.get(taskInd).markAsDone();
    }

    /**
     * Unmarks a task as done at a specified index
     *
     * @param taskInd index of the task to be unmarked
     */
    public void markAsUndone(int taskInd) {
        tasks.get(taskInd).markAsUndone();
    }

    /**
     * Returns the string representation of a task at the specified index,
     * for saving to storage.
     *
     * @param ind index of the task
     */
    public String getTaskString(int ind) {
        return tasks.get(ind).toString();
    }
}
