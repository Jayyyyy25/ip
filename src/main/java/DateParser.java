import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateParser {
    private static final List<DateTimeFormatter> DATE_FORMATS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
            DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("HH:mm yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("HHmm yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("HHmm yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("HHmm dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("HHmm dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy")
    );

    public static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                if (formatter.toString().contains("H")) {
                    return LocalDateTime.parse(input, formatter);
                } else {
                    return LocalDate.parse(input, formatter).atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Unparseable date: " + input);
    }
}