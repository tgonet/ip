package Tom.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * Extends {@link Task}.
 */

 
public class Deadline extends Task {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
    private LocalDateTime deadline;

    /**
     * Constructs a Deadline task with a name and a deadline.
     *
     * @param name     Name/description of the task.
     * @param deadline The date and time by which the task must be completed.
     */

    public Deadline(String name, LocalDateTime deadline) {
        super(name);
        this.deadline = deadline;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return The task's deadline as a {@link LocalDateTime}.
     */

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return "[" + "D" + "]" + super.toString() + " (by: " + this.deadline.format(formatter) + ")";
    }

    /**
     * Returns a string representation of the task for saving to a file.
     *
     * @return Formatted string suitable for file storage.
     */

    public String toFileString() {
        return "D" + "," + super.toFileString() + "," + this.deadline.format(formatter);
    }

    /**
     * Creates a Deadline task from a file string array.
     *
     * @param fileString Array of strings read from the file representing the task.
     * @return A {@link Deadline} object with appropriate values and status.
     */

    public static Deadline fromFileString(String[] fileString) {
        Deadline d = new Deadline(fileString[2], LocalDateTime.parse(fileString[3], formatter));
        if (fileString[1].equals("X")) {
            d.mark();
        }
        return d;
    }
}
