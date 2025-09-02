package remy.task;

import remy.exception.InvalidArgumentException;
import remy.exception.RemyException;

import remy.util.Parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

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

    public int getSize() {
        return tasks.size();
    }

    public Task deleteItem(int ind) {
        Task task  = tasks.get(ind);
        tasks.remove(task);
        return task;
    }

    public String getTaskStatus(int ind) {
        return tasks.get(ind).getStatus();
    }

    public int addItem(Task task) {
        tasks.add(task);
        return tasks.indexOf(task);
    }

    public void markAsDone(int taskInd) {
        tasks.get(taskInd).markAsDone();
    }

    public void markAsUndone(int taskInd) {
        tasks.get(taskInd).markAsUndone();
    }

    public String getTaskString(int ind) {
        return tasks.get(ind).toString();
    }
}
