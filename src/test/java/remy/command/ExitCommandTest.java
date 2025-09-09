package remy.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remy.task.TaskList;
import remy.util.Storage;
import remy.util.Ui;

public class ExitCommandTest {
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
    void testExecuteExit() {
        ExitCommand cmd = new ExitCommand();
        cmd.execute(tasks, ui, storage);

        assertTrue(ui.exitWasCalled);
    }

    // --------- Mock dependencies ---------

    static class MockUi extends Ui {
        private boolean exitWasCalled = false;

        @Override
        public String showBye() {
            exitWasCalled = true;
            return "";
        }
    }

    static class MockStorage extends Storage {

    }
}

