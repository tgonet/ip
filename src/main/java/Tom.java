import java.util.Scanner;
import java.util.ArrayList;

public class Tom {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> ls = new ArrayList<Task>();

        String logo = " _____ ___  __  __ \n"
                + "|_   _/ _ \\|  \\/  |\n"
                + "  | || | | | |\\/| |\n"
                + "  | || |_| | |  | |\n"
                + "  |_| \\___/|_|  |_|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do u need me to do?");
        System.out.println("____________________________________________________________");

        String input;
        while (true) {
            input = sc.nextLine();
            if (input.equals("bye")) {
                printRes("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                viewList(ls);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < ls.size()) {
                    Task task = ls.get(index);
                    if (!task.getMarked()) {
                        task.mark();
                        printRes("Nice! I've marked this task as done: " + task.toString());
                    }
                    else {
                        printRes("Task is already marked.");
                    }
                }
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < ls.size()) {
                    Task task = ls.get(index);
                    if (task.getMarked()) {
                        task.mark();
                        printRes("I've unmarked this task as done: " + task.toString());
                    }
                    else {
                        printRes("Task is already unmarked.");
                    }
                }
            } else {
                if (ls.size() < 100) {
                    String task = input.split(" ")[0];
                    String[] val;
                    switch (task) {
                        case "deadline":
                            val = input.substring(9).trim().split("/by", 2);
                            ls.add(new Deadline(val[0], val[1].trim()));
                            break;

                        case "todo":
                            ls.add(new ToDo(input.substring(5).trim()));
                            break;

                        case "event":
                            val = input.substring(6).trim().split("/from", 2);
                            String[] val2 = val[1].split("/to", 2);
                            System.out.println(val[0]);
                            System.out.println(val[1]);

                            System.out.println(val2[0]);

                            System.out.println(val2[1]);

                            ls.add(new Events(val[0], val2[0].trim(), val2[1].trim()));
                    }

                    printRes(String.format("Got it. I've added this task: \n%s \nNow you have %d task in your list", ls.get(ls.size() -1).toString(), ls.size()));
                }
            }
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
