package tom.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs over a period of time with a start and end.
 * Extends {@link Task}.
 */

public class Events extends Task {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Constructs an Events task with a name, start time, and end time.
     *
     * @param name Name/description of the event.
     * @param start The start date and time of the event.
     * @param end The end date and time of the event.
     */

    public Events(String name, LocalDateTime start, LocalDateTime end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start time of the event.
     *
     * @return Start date and time as a {@link LocalDateTime}.
     */

    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Returns the end time of the event.
     *
     * @return End date and time as a {@link LocalDateTime}.
     */

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[" + "E" + "]" + super.toString() + " (from: "
                + this.start.format(formatter) + " to: "
                + this.end.format(formatter) + ")";
    }

    /**
     * Returns a string representation of the event for saving to a file.
     *
     * @return Formatted string suitable for file storage.
     */

    public String toFileString() {
        return "E" + "," + super.toFileString() + "," + this.start.format(formatter) + ","
                + this.end.format(formatter);
    }

    /**
     * Creates an Events task from a file string array.
     *
     * @param fileString Array of strings read from the file representing the event.
     * @return An {@link Events} object with appropriate values and status.
     */
    
    public static Events fromFileString(String[] fileString) {
        Events e = new Events(fileString[2], LocalDateTime.parse(fileString[3], formatter),
                LocalDateTime.parse(fileString[4], formatter));
        if (fileString[1].equals("X")) {
            e.mark();
        }
        return e;
    }

    /**
     * Compares this {@code Events} object to another object for equality.
     * <p>
     * Two {@code Events} objects are considered equal if:
     * <ul>
     *     <li>They are of the same class.</li>
     *     <li>Their names are equal.</li>
     *     <li>Their start times are equal.</li>
     *     <li>Their end times are equal.</li>
     * </ul>
     * </p>
     *
     * @param obj The object to compare with this {@code Events}.
     * @return {@code true} if the specified object is equal to this {@code Events};
     *         {@code false} otherwise.
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Events other = (Events) obj;
        return this.getName().equals(other.getName())
                && this.start.equals(other.start)
                && this.end.equals(other.end);
    }
}
