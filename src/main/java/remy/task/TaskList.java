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

    public List<String> getListing(LocalDate specifiedDate, String keyword) {
        List<String> list;
        if (specifiedDate == null && keyword.isEmpty()) {
            list = tasks.stream()
                    .map(Task::getStatus)
                    .toList();
        } else if (specifiedDate != null) {
            list = tasks.stream()
                    .filter(task -> task.isCovered(specifiedDate))
                    .map(Task::getStatus)
                    .toList();
        } else {
            list = tasks.stream()
                    .filter(task -> task.toString().toLowerCase().contains(keyword))
                    .map(Task::getStatus)
                    .toList();
        }
        return list;
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
