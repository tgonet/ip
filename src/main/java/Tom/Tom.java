
package Tom;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Tom.CustomUI.DialogBox;
import Tom.Exception.TomException;
import Tom.FileManager.FileManager;
import Tom.Task.Task;
import Tom.TaskManager.TaskManager;
import Tom.UI.UI;

public class Tom {

    private FileManager fileManager;
    private TaskManager taskManager;
    private UI ui;

    public Tom(String filePath) {
        try {
            this.ui = new UI();
            this.fileManager = new FileManager(filePath);
            ArrayList<Task> ls = fileManager.getFileContents();
            this.taskManager = new TaskManager(ls, ui);
        } catch (FileNotFoundException e) {
            ui.printError("An Error occured please try again.");
        }
    }

    /**
     * Generates a response for the user's chat message.
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
            if (taskManager.getSize() < 100) {
                Task task = this.taskManager.addTask(input, fileManager);
                return String.format("Got it. I've added this task: \n%s \nNow you have %d task in your list",
                        task.toString(), this.taskManager.getSize());
            }
        }

        return "";
    }

    public static void main(String[] args) {

    }

}