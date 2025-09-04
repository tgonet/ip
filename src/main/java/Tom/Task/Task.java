package Tom.Task;

/**
 * Represents a general task with a name and a completion status.
 */

public class Task {
    private String name;
    private Boolean isMarked;

    /**
     * Constructs a Task with a given name.
     * The task is initially unmarked (incomplete).
     *
     * @param name Name/description of the task.
     */

    public Task(String name) {
        this.name = name;
        this.isMarked = false;
    }

    /**
     * Toggles the task's completion status.
     * If marked, it becomes unmarked; if unmarked, it becomes marked.
     */

    public void mark() {
        this.isMarked = !this.isMarked;
    }

    /**
     * Returns the name of the task.
     *
     * @return The task's name.
     */

    public String getName() {
        return this.name;
    }

    /**
     * Returns whether the task is marked as complete.
     *
     * @return true if the task is marked, false otherwise.
     */

    public Boolean getIsMarked() {
        return this.isMarked;
    }

    @Override
    public String toString() {
        String val = this.isMarked ? "X" : " ";
        return "[" + val + "] " + this.name;
    }

    /**
     * Returns a string representation of the task for saving to a file.
     * Includes the task's status and name, separated by a comma.
     *
     * @return Formatted string suitable for file storage.
     */

    public String toFileString() {
        String val = this.isMarked ? "X" : " ";
        return val + "," + this.name;
    }
}
