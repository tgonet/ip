import java.util.Scanner;

public class Tom {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String logo = " _____ ___  __  __ \n"
             + "|_   _/ _ \\|  \\/  |\n"
             + "  | || | | | |\\/| |\n"
             + "  | || |_| | |  | |\n"
             + "  |_| \\___/|_|  |_|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do u need me to do?");
        System.out.println("____________________________________________________________");

        String name;
        while (true) {
            name = sc.nextLine();
            if (name.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }
            System.out.println("____________________________________________________________");
            System.out.println(name);
            System.out.println("____________________________________________________________");
        }
    }
}
