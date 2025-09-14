package tom.task;

/**
 * Represents a task without a specific deadline or duration.
 * Extends {@link Task}.
 */

public class ToDo extends Task {

    /**
     * Constructs a ToDo task with a given name.
     *
     * @param name Name/description of the task.
     */

    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[" + "T" + "]" + super.toString();
    }

    /**
     * Returns a string representation of the ToDo task for saving to a file.
     *
     * @return Formatted string suitable for file storage.
     */

    public String toFileString() {
        return "T" + "," + super.toFileString();
    }

    /**
     * Creates a ToDo task from a file string array.
     *
     * @param fileString Array of strings read from the file representing the task.
     * @return A {@link ToDo} object with appropriate values and status.
     */
    
    public static ToDo fromFileString(String[] fileString) {
        ToDo t = new ToDo(fileString[2]);
        if (fileString[1].equals("X")) {
            t.mark();
        }
        return t;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ToDo other = (ToDo) obj;
        return this.getName().equals(other.getName());
    }
}
