package remy.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTaskTest {

    @Test
    public void statusStringConversion() {
        assertEquals("[T][ ] Find internships", new TodoTask("Find internships", false).getStatus());
    }

    @Test
    public void textStringConversion() {
        assertEquals("T | 0 | Find internships", new TodoTask("Find internships", false).toString());
    }
}
