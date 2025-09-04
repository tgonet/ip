package Tom.UI;

public class UI {
    public void printWelcome() {
        String logo = " _____ ___  __  __ \n"
                + "|_   _/ _ \\|  \\/  |\n"
                + "  | || | | | |\\/| |\n"
                + "  | || |_| | |  | |\n"
                + "  |_| \\___/|_|  |_|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do u need me to do?");
        System.out.println("____________________________________________________________");
    }

    public void printError(String content) {
        System.out.println("____________________________________________________________");
        System.out.println("Error: " + content);
        System.out.println("____________________________________________________________");
    }

    public void printRes(String res) {
        System.out.println("____________________________________________________________");
        System.out.println(res);
        System.out.println("____________________________________________________________");
    }

    public void printEnd(String content) {
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }

    public void print(String content) {
        System.out.println(content);
    }

    public void printLineBreak() {
        System.out.println("____________________________________________________________");
    }
}
