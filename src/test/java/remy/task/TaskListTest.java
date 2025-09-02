package remy.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remy.exception.RemyException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList tasks;
    private Task task;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        task = new Task("Sample task", false);
    }

    @Test
    void testAddItem_withTaskListSizeIncreases() {
        assertEquals(0, tasks.getSize());
        int index = tasks.addItem(task);
        assertEquals(1, tasks.getSize());
        assertEquals("0 | Sample task", tasks.getTaskString(index));
    }

    @Test
    void testMarkAsDoneAndUndone() {
        tasks.addItem(task);
        tasks.markAsDone(0);
        assertTrue(tasks.getTaskStatus(0).contains("[X]"));
        tasks.markAsUndone(0);
        assertTrue(tasks.getTaskStatus(0).contains("[ ]"));
    }

    @Test
    void testDeleteItemRemovesCorrectTask() {
        tasks.addItem(task);
        Task removed = tasks.deleteItem(0);
        assertEquals(task, removed);
        assertEquals(0, tasks.getSize());
    }

    @Test
    void testConstructorWithInvalidArguments() {
        List<String> badLines = java.util.List.of("invalidLines");
        assertThrows(RemyException.class, () -> new TaskList(badLines));
    }

    @Test
    void testGetListingWithSpecifiedDate() {
        Task datedTask = new DeadlineTask("assignment",
                LocalDateTime.parse("2025/09/01 23:59", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        tasks.addItem(datedTask);

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        tasks.getListing(LocalDate.of(2025, 9, 1), "");
        String output = out.toString();

        assertTrue(output.contains("Here are the tasks on the specified date."));
        assertTrue(output.contains("assignment"));
    }
}
