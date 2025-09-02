package remy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remy.task.TaskList;

import remy.util.Storage;
import remy.util.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListCommandTest {
    private TaskList tasks;
    private MockUi ui;
    private MockStorage storage;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new MockUi();
        storage = new MockStorage();
    }

    @Test
    void testExecuteListing() {
        AddCommand addCmd = new AddCommand("Reading");
        addCmd.execute(tasks, ui, storage);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        ListCommand listCmd = new ListCommand(null);
        listCmd.execute(tasks, ui, storage);

        System.setOut(System.out);
        String output = out.toString();

        assertTrue(output.contains(tasks.getTaskStatus(0)));
    }

    @Test
    void testExecuteListingWithSpecifiedDate() {
        LocalDateTime date = LocalDateTime.parse("2025/09/01 23:59", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        AddCommand addCmd = new AddCommand("Assignment", date);
        addCmd.execute(tasks, ui, storage);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        ListCommand listCmd = new ListCommand(date.toLocalDate());
        listCmd.execute(tasks, ui, storage);

        System.setOut(System.out);
        String output = out.toString();

        assertTrue(output.contains(tasks.getTaskStatus(0)));
    }

    @Test
    void testExecuteListingNoTasks() {
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        ListCommand listCmd = new ListCommand(null);
        listCmd.execute(tasks, ui, storage);

        System.setOut(System.out);
        String output = out.toString();

        assertTrue(output.contains("You have no tasks in the list at this moment."));
    }

    // --------- Mock dependencies ---------

    static class MockUi extends Ui {

    }

    static class MockStorage extends Storage {

    }
}

