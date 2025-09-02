package remy.util;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
    private Path filePath;

    public Storage() {
        this.filePath = Path.of("./data/remy.txt");
    }

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
        createFile();
    }

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

    public List<String> load() {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Error reading storage file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void updateLine(int lineNumber, String newLineContent) throws IOException {
        List<String> lines = Files.readAllLines(filePath);

        if (lineNumber < 0 || lineNumber >= lines.size()) {
            throw new IllegalArgumentException("Invalid line number");
        }

        lines.set(lineNumber, newLineContent);
        Files.write(filePath, lines);
    }

    public void appendLine(String newLine) throws IOException {
        Files.write(filePath,
                Arrays.asList(newLine),
                StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    public void deleteLine(int lineNumber) throws IOException {
        List<String> lines = Files.readAllLines(filePath);

        if (lineNumber < 0 || lineNumber >= lines.size()) {
            throw new IllegalArgumentException("Invalid line number");
        }

        lines.remove(lineNumber);
        Files.write(filePath, lines);
    }
}
