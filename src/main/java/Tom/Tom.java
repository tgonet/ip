
package tom;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import tom.exception.TomException;
import tom.filemanager.FileManager;
import tom.task.Task;
import tom.taskmanager.TaskManager;
import tom.ui.UI;

public class Tom {

    private FileManager fileManager;
    private TaskManager taskManager;
    private UI ui;

    public Tom(String filePath) {
        try {
            this.ui = new UI();
            this.fileManager = new FileManager(filePath);
            ArrayList<Task> ls = fileManager.getFileContents();
            this.taskManager = new TaskManager(ls);
        } catch (FileNotFoundException e) {
            ui.printError("An Error occured please try again.");
        }
    }

    /**
     * Processes user input and generates the appropriate response string.
     * <p>
     * This method acts as the main command handler for the application. It:
     * <ul>
     *     <li>Handles simple commands such as {@code bye} and {@code list}.</li>
     *     <li>Handles task-related operations such as marking, unmarking, deleting,
     *         checking occurrences, and finding tasks.</li>
     *     <li>Adds a new task if the input does not match any known commands.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Supported commands:
     * <ul>
     *     <li>{@code bye} – exits the program.</li>
     *     <li>{@code list} – displays the task list.</li>
     *     <li>{@code mark [index]} – marks a task as done.</li>
     *     <li>{@code unmark [index]} – marks a task as not done.</li>
     *     <li>{@code delete [index]} – deletes a task.</li>
     *     <li>{@code occur [yyyy-MM-dd HH:mm]} – checks for tasks occurring at the specified time.</li>
     *     <li>{@code find [keyword]} – finds tasks with similar descriptions.</li>
     *     <li>Any other input is treated as a new task to add.</li>
     * </ul>
     * </p>
     *
     * @param input The user input command string.
     * @return A string representing the application's response to the user command.
     * @throws TomException If the command cannot be processed due to invalid input,
     *                      file I/O errors, or other application-specific issues.
     */

    public String getResponse(String input) throws TomException {
        if (input.equals("bye")) {
            return "Bye. Hope to see you again soon!";
        } else if (input.equals("list")) {
            return this.taskManager.viewList();
        } else if (input.startsWith("mark ")) {
            Task task = this.taskManager.mark(input, true, fileManager);
            return "Nice! I've marked this task as done: " + task.toString();
        } else if (input.startsWith("unmark ")) {
            Task task = this.taskManager.mark(input, false, fileManager);
            return "I've unmarked this task as done: " + task.toString();
        } else if (input.startsWith("delete ")) {
            Task t = this.taskManager.removeTask(input, fileManager);
            return String.format("Noted. I've removed this task:\n %s \nNow you have %d tasks in the list.", t,
                    this.taskManager.getSize());
        } else if (input.startsWith("occur ")) {
            return taskManager.checkOccuringDates(input);
        } else if (input.startsWith("find")) {
            return taskManager.findSimilarDescriptions(input);
        } else {
            assert taskManager.getSize() >= 0 : "TaskManager size should never be negative";
            Task task = this.taskManager.addTaskProcessor(input, fileManager);
            return String.format("Got it. I've added this task: \n%s \nNow you have %d task in your list",
                    task.toString(), this.taskManager.getSize());
        }
    }

    public static void main(String[] args) {

    }

}