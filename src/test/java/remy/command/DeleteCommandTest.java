package remy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remy.task.Task;
import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

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
        assertTrue(ui.isDeleteCalled);
        assertEquals(-1, ui.lastIndex);
    }

    // --------- Mock dependencies ---------

    static class MockUi extends Ui {
        private boolean isDeleteCalled = false;
        private int lastIndex = -1;

        @Override
        public String showDeleted(TaskList tasks, Task task) {
            isDeleteCalled = true;
            lastIndex = -1;
            return "";
        }
    }

    static class MockStorage extends Storage {
        private String lastAppendedLine = null;

        @Override
        public void deleteLine(int line) {
            lastAppendedLine = null;
        }
    }
}

