package Tom;

import java.util.Scanner;

import Tom.Exception.TomException;
import Tom.FileManager.FileManager;
import Tom.Task.Task;
import Tom.TaskManager.TaskManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tom {

    FileManager fileManager;
    TaskManager taskManager;
    String filePath;

    public Tom(String filePath) {
        try {
            this.fileManager = new FileManager(filePath);
            ArrayList<Task> ls = fileManager.getFileContents();
            this.taskManager = new TaskManager(ls);
        } catch (FileNotFoundException e) {
            printRes("An Error occured please try again.");
        }
    }

    public void printRes(String res) {
        System.out.println("____________________________________________________________");
        System.out.println(res);
        System.out.println("____________________________________________________________");
    }

    public void run() {
        String input;

        Scanner sc = new Scanner(System.in);

        String logo = " _____ ___  __  __ \n"
                + "|_   _/ _ \\|  \\/  |\n"
                + "  | || | | | |\\/| |\n"
                + "  | || |_| | |  | |\n"
                + "  |_| \\___/|_|  |_|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do u need me to do?");
        System.out.println("____________________________________________________________");

        while (true) {
            input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    printRes("Bye. Hope to see you again soon!");
                    sc.close();
                    break;
                } else if (input.equals("list")) {
                    this.taskManager.viewList();
                } else if (input.startsWith("mark ")) {
                    Task task = this.taskManager.mark(input, true, fileManager);
                    printRes("Nice! I've marked this task as done: " + task.toString());

                } else if (input.startsWith("unmark ")) {
                    Task task = this.taskManager.mark(input, false, fileManager);
                    printRes("I've unmarked this task as done: " + task.toString());

                } else if (input.startsWith("delete ")) {
                    Task t = this.taskManager.removeTask(input, fileManager);
                    printRes(
                            String.format("Noted. I've removed this task:\n %s \nNow you have %d tasks in the list.", t,
                                    this.taskManager.getSize()));
                } else if (input.startsWith("occur ")) {
                    taskManager.checkOccuringDates(input);
                } else if (input.startsWith("find")) {
                    taskManager.findSimilarDescriptions(input);
                } else {
                    if (taskManager.getSize() < 100) {
                        Task task = this.taskManager.addTask(input, fileManager);
                        printRes(String.format("Got it. I've added this task: \n%s \nNow you have %d task in your list",
                                task.toString(), this.taskManager.getSize()));
                    }
                }
            } catch (TomException e) {
                printRes(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "./data/list.txt";
        Tom tom = new Tom(filePath);
        tom.run();
    }

}