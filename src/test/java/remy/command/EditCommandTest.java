package remy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remy.exception.RemyException;

import remy.task.TaskList;

import remy.util.Storage;
import remy.util.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditCommandTest {
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
    void testExecuteMarkTask() throws RemyException {
        AddCommand addCmd = new AddCommand("Reading");
        addCmd.execute(tasks, ui, storage);
        EditCommand editCmd = new EditCommand(1, 0);
        editCmd.execute(tasks, ui, storage);

        assertTrue(tasks.getTaskStatus(0).contains("[X]"));
        assertEquals(tasks.getTaskString(0), storage.lastUpdatedLine);
        assertTrue(ui.markWasCalled);
        assertEquals(0, ui.taskInd);
    }

    @Test
    void testExecuteUnmarkTask() throws RemyException {
        AddCommand addCmd = new AddCommand("Reading");
        addCmd.execute(tasks, ui, storage);
        EditCommand editCmd = new EditCommand(0, 0);
        editCmd.execute(tasks, ui, storage);

        assertTrue(tasks.getTaskStatus(0).contains("[ ]"));
        assertEquals(tasks.getTaskString(0), storage.lastUpdatedLine);
        assertTrue(ui.unmarkWasCalled);
        assertEquals(0, ui.taskInd);
    }

    @Test
    void testExecuteMarkTest_InvalidIndex_fail() throws RemyException {
        AddCommand addCmd = new AddCommand("Reading");
        addCmd.execute(tasks, ui, storage);
        EditCommand editCmd = new EditCommand(1, 1);
        assertThrows(RemyException.class, () -> editCmd.execute(tasks, ui, storage));
    }

    // --------- Mock dependencies ---------

    static class MockUi extends Ui {
        boolean markWasCalled = false;
        boolean unmarkWasCalled = false;
        int taskInd = -1;

        @Override
        public String showMark(TaskList tasks, int taskInd) {
            markWasCalled = true;
            this.taskInd = taskInd;
            return "";
        }

        @Override
        public String showUnmark(TaskList tasks, int taskInd) {
            unmarkWasCalled = true;
            this.taskInd = taskInd;
            return "";
        }
    }

    static class MockStorage extends Storage {
        String lastUpdatedLine = null;

        @Override
        public void updateLine(int lineNumber, String line) {
            lastUpdatedLine = line;
        }
    }
}

