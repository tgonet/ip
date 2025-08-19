import java.util.Scanner;
import java.util.ArrayList;

public class Tom {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> ls = new ArrayList<String>();

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
                    String task = ls.get(index);
                    if (!task.startsWith("[X]")) {
                        ls.set(index, "[X]" + task.substring(3));
                        printRes("Nice! I've marked this task as done: " + ls.get(index));
                    }
                }
            } 
            else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < ls.size()) {
                    String task = ls.get(index);
                    if (!task.startsWith("[]")) {
                        ls.set(index, "[ ]" + task.substring(3));
                        printRes("Nice! I've unmarked this task as done: " + ls.get(index));
                    }
                }
            } else {
                if (ls.size() < 100) {
                    ls.add("[ ] " + input);
                    printRes("added: " + input);
                }
            }
        }
    }

    public static void viewList(ArrayList<String> ls) {
        int count = 1;
        if (ls.size() > 0) {
            System.out.println("____________________________________________________________");
            System.out.println("Here are the tasks in your list:");
            for (String i : ls) {
                if (i == null) {
                    break;
                }
                System.out.println(count + ". " + i);
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
