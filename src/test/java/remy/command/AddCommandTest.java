package remy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remy.task.TaskList;

import remy.util.Storage;
import remy.util.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCommandTest {
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
    void testExecuteAddsTodoTask() {
        AddCommand cmd = new AddCommand("Reading");
        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.getSize());
        assertTrue(tasks.getTaskString(0).contains("Reading"));
        assertEquals(tasks.getTaskString(0), storage.lastAppendedLine);
        assertTrue(ui.addedWasCalled);
        assertEquals(0, ui.lastIndex);
    }

    @Test
    void testExecuteAddsDeadlineTask() {
        LocalDateTime deadline = LocalDateTime.parse("2025/09/01 23:59", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        AddCommand cmd = new AddCommand("Submit report", deadline);
        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.getSize());
        assertTrue(tasks.getTaskString(0).contains("Submit report"));
        assertTrue(tasks.getTaskString(0).contains("Sep 01 2025"));
        assertEquals(tasks.getTaskString(0), storage.lastAppendedLine);
        assertTrue(ui.addedWasCalled);
    }

    @Test
    void testExecuteAddsEventTask() {
        LocalDateTime from = LocalDateTime.parse("2025/09/01 15:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        LocalDateTime to = LocalDateTime.parse("2025/09/01 16:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        AddCommand cmd = new AddCommand("Meeting", from, to);
        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.getSize());
        assertTrue(tasks.getTaskString(0).contains("Meeting"));
        assertEquals(tasks.getTaskString(0), storage.lastAppendedLine);
        assertTrue(ui.addedWasCalled);
    }

    // --------- Mock dependencies ---------

    static class MockUi extends Ui {
        boolean addedWasCalled = false;
        int lastIndex = -1;

        @Override
        public String showAdded(TaskList tasks, int taskInd) {
            addedWasCalled = true;
            lastIndex = taskInd;
            return "";
        }
    }

    static class MockStorage extends Storage {
        String lastAppendedLine = null;

        @Override
        public void appendLine(String line) {
            lastAppendedLine = line;
        }
    }
}
