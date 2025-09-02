package remy.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import remy.util.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(tempDir.resolve("remy.txt").toString());
    }

    @Test
    void testAppendAndLoad() throws IOException {
        storage.appendLine("Hello");
        storage.appendLine("World");

        List<String> lines = storage.load();
        assertEquals(2, lines.size());
        assertEquals("Hello", lines.get(0));
        assertEquals("World", lines.get(1));
    }

    @Test
    void testUpdateLine() throws IOException {
        storage.appendLine("Old line");
        storage.updateLine(0, "New line");

        List<String> lines = storage.load();
        assertEquals("New line", lines.get(0));
    }

    @Test
    void testDeleteLine() throws IOException {
        storage.appendLine("Line1");
        storage.appendLine("Line2");
        storage.deleteLine(0);

        List<String> lines = storage.load();
        assertEquals(1, lines.size());
        assertEquals("Line2", lines.get(0));
    }

    @Test
    void testInvalidLineNumberThrows() {
        assertThrows(IllegalArgumentException.class, () -> storage.updateLine(0, "Fail"));
        assertThrows(IllegalArgumentException.class, () -> storage.deleteLine(0));
    }
}
