package tom.taskmanager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import tom.exception.TomException;
import tom.filemanager.FileManager;
import tom.task.Task;
import tom.task.ToDo;

public class TaskManagerTest {

    @Test
    void testCorrectInput() {
        String input = "deadline return book /by 2026-03-12 00:00";
        TaskManager tm = new TaskManager(new ArrayList<>()); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        assertDoesNotThrow(() -> {
            tm.addTaskProcessor(input, fm);
        });
    }

    @Test
    void testMissingTime() {
        String input = "deadline return book /by 2026-03-12";
        TaskManager tm = new TaskManager(new ArrayList<>()); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        TomException ex = assertThrows(TomException.class, () -> {
            tm.addTaskProcessor(input, fm);
        });

        assertTrue(ex.getMessage().contains("yyyy-MM-dd HH:mm"));
    }

    @Test
    void testMissingDate() {
        String input = "deadline return book /by 00:00";
        TaskManager tm = new TaskManager(new ArrayList<>()); // <-- your TaskManager class
        FileManager fm = new FileManager("./data/test.txt"); // <-- a real or stubbed FileManager

        TomException ex = assertThrows(TomException.class, () -> {
            tm.addTaskProcessor(input, fm);
        });

        assertTrue(ex.getMessage().contains("yyyy-MM-dd HH:mm"));
    }

    @Test
    void testAddEventTask() {
        String input = "event meeting /from 2026-03-12 10:00 /to 2026-03-12 12:00";
        TaskManager tm = new TaskManager(new ArrayList<>());
        FileManager fm = new FileManager("./data/test.txt");

        assertDoesNotThrow(() -> {
            Task t = tm.addTaskProcessor(input, fm);
            assertTrue(t.getName().contains("meeting"));
        });
    }

    @Test
    void testAddTaskLimitExceeded() {
        TaskManager tm = new TaskManager(new ArrayList<>());
        FileManager fm = new FileManager("./data/test.txt");
        for (int i = 0; i < 100; i++) {
            try {
                tm.addTask(new ToDo("Task " + i), fm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }   
        String input = "todo another task";
        TomException ex = assertThrows(TomException.class, () -> tm.addTaskProcessor(input, fm));
        assertTrue(ex.getMessage().contains("Task limit reached"));
    }

    @Test
    void testRemoveTaskInvalidIndex() {
        TaskManager tm = new TaskManager(new ArrayList<>());
        FileManager fm = new FileManager("./data/test.txt");
        String input = "remove 1";
        TomException ex = assertThrows(TomException.class, () -> tm.removeTask(input, fm));
        assertTrue(ex.getMessage().toLowerCase().contains("invalid"));
    }

    // AI Generated Test Case
    @Test
    void testAddDuplicateTaskThrows() {
        String input = "todo read book";
        TaskManager tm = new TaskManager(new ArrayList<>());
        FileManager fm = new FileManager("./data/test.txt");

        assertDoesNotThrow(() -> tm.addTaskProcessor(input, fm));
        TomException ex = assertThrows(TomException.class, () -> tm.addTaskProcessor(input, fm));
        assertTrue(ex.getMessage().toLowerCase().contains("duplicate"));
    }
}
