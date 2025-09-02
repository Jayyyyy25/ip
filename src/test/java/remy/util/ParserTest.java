package remy.util;

import org.junit.jupiter.api.Test;
import remy.command.*;
import remy.exception.*;
import remy.task.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testParseTodoCommand() throws RemyException {
        Command cmd = Parser.parseCommand("todo read book");
        assertTrue(cmd instanceof AddCommand);
    }

    @Test
    void testParseDeadlineCommand() throws RemyException {
        Command cmd = Parser.parseCommand("deadline submit report /by 2025-09-01 12:00");
        assertTrue(cmd instanceof AddCommand);
    }

    @Test
    void testParseEventCommand() throws RemyException {
        Command cmd = Parser.parseCommand("event meeting /from 2025-09-01 10:00 /to 2025-09-01 12:00");
        assertTrue(cmd instanceof AddCommand);
    }

    @Test
    void testParseInvalidCommand() {
        assertThrows(RemyException.class, () -> Parser.parseCommand("blablabla"));
    }

    @Test
    void testParseTaskTodo() throws RemyException {
        Task task = Parser.parseTask("T | 0 | read book");
        assertTrue(task instanceof TodoTask);
    }

    @Test
    void testParseTaskDeadline() throws RemyException {
        Task task = Parser.parseTask("D | 1 | submit report | Sep 01 2025 23:59");
        assertTrue(task instanceof DeadlineTask);
    }

    @Test
    void testParseDateTimeValid() {
        LocalDateTime dt = Parser.parseDateTime("2025-09-01 12:00");
        assertEquals(2025, dt.getYear());
        assertEquals(9, dt.getMonthValue());
        assertEquals(1, dt.getDayOfMonth());
        assertEquals(12, dt.getHour());
        assertEquals(0, dt.getMinute());
    }

    @Test
    void testParseDateTimeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseDateTime("invalid date"));
    }
}
