package tom.taskmanager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import tom.exception.TomException;
import tom.filemanager.FileManager;

public class TaskManagerTest {

    @Test
    void testCorrectInput() {
        String input = "deadline return book /by 2026-03-12 00:00";
        TaskManager tm = new TaskManager(new ArrayList<>()); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        assertDoesNotThrow(() -> {
            tm.addTask(input, fm);
        });
    }

    @Test
    void testMissingTime() {
        String input = "deadline return book /by 2026-03-12";
        TaskManager tm = new TaskManager(new ArrayList<>()); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        TomException ex = assertThrows(TomException.class, () -> {
            tm.addTask(input, fm);
        });

        assertTrue(ex.getMessage().contains("yyyy-MM-dd HH:mm"));
    }

    @Test
    void testMissingDate() {
        String input = "deadline return book /by 00:00";
        TaskManager tm = new TaskManager(new ArrayList<>()); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        TomException ex = assertThrows(TomException.class, () -> {
            tm.addTask(input, fm);
        });

        assertTrue(ex.getMessage().contains("yyyy-MM-dd HH:mm"));
    }
}
