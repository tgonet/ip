package Tom;

import java.util.Scanner;

import Tom.Exception.TomException;
import Tom.FileManager.FileManager;
import Tom.Task.Task;
import Tom.TaskManager.TaskManager;
import Tom.UI.UI;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tom {

    FileManager fileManager;
    TaskManager taskManager;
    UI ui;
    String filePath;

    public Tom(String filePath) {
        try {
            this.ui = new UI();
            this.fileManager = new FileManager(filePath);
            ArrayList<Task> ls = fileManager.getFileContents();
            this.taskManager = new TaskManager(ls,ui);
        } catch (FileNotFoundException e) {
            ui.printError("An Error occured please try again.");
        }
    }


    public void run() {
        String input;

        Scanner sc = new Scanner(System.in);
        ui.printWelcome();
        

        while (true) {
            input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    ui.printRes("Bye. Hope to see you again soon!");
                    sc.close();
                    break;
                } else if (input.equals("list")) {
                    this.taskManager.viewList();
                } else if (input.startsWith("mark ")) {
                    Task task = this.taskManager.mark(input, true, fileManager);
                    ui.printRes("Nice! I've marked this task as done: " + task.toString());

                } else if (input.startsWith("unmark ")) {
                    Task task = this.taskManager.mark(input, false, fileManager);
                    ui.printRes("I've unmarked this task as done: " + task.toString());

                } else if (input.startsWith("delete ")) {
                    Task t = this.taskManager.removeTask(input, fileManager);
                    ui.printRes(
                            String.format("Noted. I've removed this task:\n %s \nNow you have %d tasks in the list.", t,
                                    this.taskManager.getSize()));
                } else if (input.startsWith("occur ")) {
                    taskManager.checkOccuringDates(input);
                } else if (input.startsWith("find")) {
                    taskManager.findSimilarDescriptions(input);
                } else {
                    if (taskManager.getSize() < 100) {
                        Task task = this.taskManager.addTask(input, fileManager);
                        ui.printRes(String.format("Got it. I've added this task: \n%s \nNow you have %d task in your list",
                                task.toString(), this.taskManager.getSize()));
                    }
                }
            } catch (TomException e) {
                ui.printRes(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "./data/list.txt";
        Tom tom = new Tom(filePath);
        tom.run();
    }

}