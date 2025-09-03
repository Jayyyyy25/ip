package remy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remy.task.Task;
import remy.task.TaskList;

import remy.util.Storage;
import remy.util.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCommandTest {
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
    void testDeleteTask() {
        AddCommand addCmd = new AddCommand("Reading");
        addCmd.execute(tasks, ui, storage);
        DeleteCommand delCmd = new DeleteCommand(0);
        delCmd.execute(tasks, ui, storage);

        assertEquals(0, tasks.getSize());
        assertEquals(null, storage.lastAppendedLine);
        assertTrue(ui.deletedWasCalled);
        assertEquals(-1, ui.lastIndex);
    }

    // --------- Mock dependencies ---------

    static class MockUi extends Ui {
        boolean deletedWasCalled = false;
        int lastIndex = -1;

        @Override
        public String showDeleted(TaskList tasks, Task task) {
            deletedWasCalled = true;
            lastIndex = -1;
            return "";
        }
    }

    static class MockStorage extends Storage {
        String lastAppendedLine = null;

        @Override
        public void deleteLine(int line) {
            lastAppendedLine = null;
        }
    }
}

