package Tom.TaskManager;

import Tom.Exception.TomException;
import Tom.FileManager.FileManager;
import Tom.UI.UI;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    UI ui = new UI();

    @Test
    void testCorrectInput() {
        String input = "deadline return book /by 2026-03-12 00:00";
        TaskManager tm = new TaskManager(new ArrayList<>(), ui); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        assertDoesNotThrow(() -> {
            tm.addTask(input, fm);
        });
    }

    @Test
    void testMissingTime() {
        String input = "deadline return book /by 2026-03-12";
        TaskManager tm = new TaskManager(new ArrayList<>(), ui); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        TomException ex = assertThrows(TomException.class, () -> {
            tm.addTask(input, fm);
        });

        assertTrue(ex.getMessage().contains("yyyy-MM-dd HH:mm"));
    }

    @Test
    void testMissingDate() {
        String input = "deadline return book /by 00:00";
        TaskManager tm = new TaskManager(new ArrayList<>(), ui); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        TomException ex = assertThrows(TomException.class, () -> {
            tm.addTask(input, fm);
        });

        assertTrue(ex.getMessage().contains("yyyy-MM-dd HH:mm"));
    }
}
