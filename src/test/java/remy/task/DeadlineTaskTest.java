package remy.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTaskTest {

    @Test
    public void statusStringConversion() {
        assertEquals("[D][ ] Do assignment (by: Sep 01 2025 23:59)",
                new DeadlineTask("Do assignment",
                        LocalDateTime.parse("2025/09/01 23:59",
                                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).getStatus());
    }

    @Test
    public void textStringConversion() {
        assertEquals("D | 0 | Do assignment | Sep 01 2025 23:59",
                new DeadlineTask("Do assignment",
                        LocalDateTime.parse("2025/09/01 23:59",
                                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).toString());
    }

    @Test
    public void isCovered_sameDay_success() {
        assertTrue(new DeadlineTask("Do assignment",
                LocalDateTime.parse("2025/09/01 23:59",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).isCovered(
                                LocalDate.parse("2025/09/01 18:00",
                                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))));
    }

    @Test
    public void isCovered_diffDay_fail() {
        assertFalse(new DeadlineTask("Do assignment",
                LocalDateTime.parse("2025/09/01 23:59",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).isCovered(
                LocalDate.parse("2025/09/02 00:00",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))));
    }
}
