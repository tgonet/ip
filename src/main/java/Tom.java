import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Tom {
    public static void main(String[] args) {
        String filePath = "./data/list.txt";
        FileManager fileManager = new FileManager(filePath);
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> ls = new ArrayList<Task>();
        try {
            ls = fileManager.getFileContents();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        }

        String logo = " _____ ___  __  __ \n"
                + "|_   _/ _ \\|  \\/  |\n"
                + "  | || | | | |\\/| |\n"
                + "  | || |_| | |  | |\n"
                + "  |_| \\___/|_|  |_|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do u need me to do?");
        System.out.println("____________________________________________________________");

        String input;
        int index;
        while (true) {
            input = sc.nextLine();
            if (input.equals("bye")) {
                printRes("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                viewList(ls);
            } else if (input.startsWith("mark ")) {
                index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < ls.size()) {
                    Task task = ls.get(index);
                    if (!task.getMarked()) {
                        task.mark();
                        try {
                            fileManager.writeToFile(ls);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println(e.getMessage() + "Please try again");
                        }
                        printRes("Nice! I've marked this task as done: " + task.toString());
                    } else {
                        printRes("Task is already marked.");
                    }
                }
            } else if (input.startsWith("unmark ")) {
                index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < ls.size()) {
                    Task task = ls.get(index);
                    if (task.getMarked()) {
                        task.mark();
                        try {
                            fileManager.writeToFile(ls);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println(e.getMessage() + "Please try again");
                        }
                        printRes("I've unmarked this task as done: " + task.toString());
                    } else {
                        printRes("Task is already unmarked.");
                    }
                }
            } else if (input.startsWith("delete ")) {
                index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < ls.size()) {
                    removeTask(index, ls, fileManager);
                } else {
                    printRes("This is an invalid task");
                }
            } else {
                if (ls.size() < 100) {
                    try {
                        addTask(input, ls, fileManager);
                        printRes(String.format("Got it. I've added this task: \n%s \nNow you have %d task in your list",
                                ls.get(ls.size() - 1).toString(), ls.size()));
                    } catch (Exception e) {
                        printRes(e.toString());
                    }
                }
            }
        }
    }

    public static void removeTask(int i, ArrayList<Task> ls, FileManager fileManager) {
        try {
            Task t = ls.remove(i);
            fileManager.writeToFile(ls);
            printRes(String.format("Noted. I've removed this task:\n %s \nNow you have %d tasks in the list.", t,
                    ls.size()));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    public static void addTask(String input, ArrayList<Task> ls, FileManager fileManager) throws TomException {
        String task = input.split(" ")[0];
        String[] val;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String description, start, end;

        try {
            switch (task) {
                case "deadline":
                    if (!input.contains("/by")) {
                        throw new TomException(
                                "Please enter in this format \"deadline [description] /by [yyyy-MM-dd HH:mm] \"");
                    }
                    val = input.substring(9).trim().split("/by", 2);
                    if (val[0].isBlank() || val[1].isBlank()) {
                        throw new TomException(
                                "Please enter in this format \"deadline [description] /by [yyyy-MM-dd HH:mm] \"");
                    }
                    Deadline d = new Deadline(val[0].trim(), LocalDateTime.parse(val[1].trim(), fmt));
                    ls.add(d);
                    fileManager.appendToFile(String.format("%s\n", d.toFileString()));
                    break;

                case "todo":
                    description = input.substring(4).trim();
                    if (description.isBlank()) {
                        throw new TomException(
                                "You are missing a description. Enter in this format \"todo [yyyy-MM-dd HH:mm]\"");
                    }
                    ToDo t = new ToDo(description);
                    ls.add(new ToDo(description));
                    fileManager.appendToFile(String.format("%s\n", t.toFileString()));
                    break;

                case "event":
                    val = input.substring(6).trim().split("/from", 2);
                    if (!input.contains("/from") || !input.contains("/to")) {
                        throw new TomException(
                                "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm] /to [yyyy-MM-dd HH:mm] \"");
                    }
                    String[] val2 = val[1].split("/to", 2);
                    if (val[0].isBlank() || val2[0].isBlank() || val2[1].isBlank()) {
                        throw new TomException(
                                "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm] /to [yyyy-MM-dd HH:mm] \"");
                    }
                    Events e = new Events(val[0].trim(), LocalDateTime.parse(val2[0].trim(), fmt),
                            LocalDateTime.parse(val2[1].trim(), fmt));
                    ls.add(e);
                    fileManager.appendToFile(String.format("%s\n", e.toFileString()));
                    break;
                default:
                    throw new TomException("Please enter something that is under my control");
            }
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static void viewList(ArrayList<Task> ls) {
        int count = 1;
        if (ls.size() > 0) {
            System.out.println("____________________________________________________________");
            System.out.println("Here are the tasks in your list:");
            for (Task i : ls) {
                System.out.println(count + ". " + i.toString());
                count++;
            }
            System.out.println("____________________________________________________________");
        } else {
            printRes("You have not added anything to the list!");
        }
    }

    public static void printRes(String res) {
        System.out.println("____________________________________________________________");
        System.out.println(res);
        System.out.println("____________________________________________________________");
    }
}
