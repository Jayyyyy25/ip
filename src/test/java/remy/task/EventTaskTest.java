package remy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class EventTaskTest {

    @Test
    public void statusStringConversion() {
        assertEquals("[E][ ] Dinner with mentor (from: Sep 01 2025 18:00, to: Sep 01 2025 20:00)",
                new EventTask("Dinner with mentor",
                        LocalDateTime.parse("2025/09/01 18:00",
                                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                        LocalDateTime.parse("2025/09/01 20:00",
                                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).getStatus());
    }

    @Test
    public void textStringConversion() {
        assertEquals("E | 0 | Dinner with mentor | Sep 01 2025 18:00 | Sep 01 2025 20:00",
                new EventTask("Dinner with mentor",
                        LocalDateTime.parse("2025/09/01 18:00",
                                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                        LocalDateTime.parse("2025/09/01 20:00",
                                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).toString());
    }

    @Test
    public void isCovered_sameDay_success() {
        assertTrue(new EventTask("Dinner with mentor",
                LocalDateTime.parse("2025/09/01 18:00",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                LocalDateTime.parse("2025/09/01 20:00",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).isCovered(
                LocalDate.parse("2025/09/01",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
    }

    @Test
    public void isCovered_diffDay_fail() {
        assertFalse(new EventTask("Dinner with mentor",
                LocalDateTime.parse("2025/09/01 18:00",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                LocalDateTime.parse("2025/09/01 20:00",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), false).isCovered(
                LocalDate.parse("2025/09/02",
                        DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
    }
}

