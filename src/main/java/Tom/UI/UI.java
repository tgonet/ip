package Tom.UI;

/**
 * The {@code UI} class provides simple text-based user interface methods for
 * printing messages, errors, results, and formatted output to the console.
 * <p>
 * It also includes a welcome banner and utility methods for standardized
 * line breaks.
 */
public class UI {

    /**
     * Prints a welcome message along with an ASCII art logo to the console.
     */
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

    /**
     * Prints an error message with a standard formatting.
     *
     * @param content the error description to display
     */
    public void printError(String content) {
        System.out.println("____________________________________________________________");
        System.out.println("Error: " + content);
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a result message with a standard formatting.
     *
     * @param res the result content to display
     */
    public void printRes(String res) {
        System.out.println("____________________________________________________________");
        System.out.println(res);
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints an ending message followed by a line break.
     *
     * @param content the ending message to display
     */
    public void printEnd(String content) {
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the given content to the console without additional formatting.
     *
     * @param content the text to display
     */
    public void print(String content) {
        System.out.println(content);
    }

    /**
     * Prints a line break (horizontal line) for formatting output.
     */
    public void printLineBreak() {
        System.out.println("____________________________________________________________");
    }
}
