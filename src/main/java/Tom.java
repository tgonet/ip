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
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                int count = 1;
                if (ls.size() > 0) {
                    System.out.println("____________________________________________________________");
                    for (String i : ls) {
                        if (i == null) {
                            break;
                        }
                        System.out.println(count + ". " + i);
                        count++;
                    }
                    System.out.println("____________________________________________________________");
                }
                else {
                    System.out.println("____________________________________________________________");
                    System.out.println("You have not added anything to the list!");
                    System.out.println("____________________________________________________________");
                }
            } else {
                if (ls.size() < 100) {
                    ls.add(input);
                    System.out.println("____________________________________________________________");
                    System.out.println("added: " + input);
                    System.out.println("____________________________________________________________");
                }
            }

        }
    }
}
