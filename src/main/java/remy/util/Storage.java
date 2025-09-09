package remy.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles loading and updating the storage file
 */
public class Storage {
    private Path filePath;

    /**
     * Creates a Storage instance with default file path ./data/remy.txt
     */
    public Storage() {
        this.filePath = Path.of("./data/remy.txt");
        createFile();
    }

    /**
     * Creates a Storage instance with custom file path
     *
     * @param filePath the path to the Storage file
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
        createFile();
    }

    /**
     * Creates a file and its directories, if they do not exist
     */
    private void createFile() {
        try {
            if (Files.notExists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.out.println("Error creating storage file: " + e.getMessage());
        }
    }

    /**
     * Reads all the lines from the file path
     *
     * @return Lines in the file in a list of String
     */
    public List<String> load() {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Error reading storage file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Updates a specific task line in the storage file.
     * Usually used when mark or unmark as done for a task
     *
     * @param lineNumber the zero-based index of line which requires update
     * @param newLineContent the new content to replace the existing line
     * @throws IOException if an I/O error occurs while reading or writing the file
     * @throws IllegalArgumentException if the {@code lineNumber} is out of range
     */
    public void updateLine(int lineNumber, String newLineContent) throws IOException {
        List<String> lines = Files.readAllLines(filePath);

        if (lineNumber < 0 || lineNumber >= lines.size()) {
            throw new IllegalArgumentException("Invalid line number");
        }

        lines.set(lineNumber, newLineContent);
        Files.write(filePath, lines);
    }

    /**
     * Adds a new line at the bottom of the file
     *
     * @param newLine the content of new line to be appended
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void appendLine(String newLine) throws IOException {
        Files.write(filePath,
                Arrays.asList(newLine),
                StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    /**
     * Deletes a line with given line number
     *
     * @param lineNumber the zero-based index of the line to delete
     * @throws IOException if an I/O error occurs when reading or writing to the file
     * @throws IllegalArgumentException if the {@code lineNumber} is out of range
     */
    public void deleteLine(int lineNumber) throws IOException {
        List<String> lines = Files.readAllLines(filePath);

        if (lineNumber < 0 || lineNumber >= lines.size()) {
            throw new IllegalArgumentException("Invalid line number");
        }

        lines.remove(lineNumber);
        Files.write(filePath, lines);
    }
}
