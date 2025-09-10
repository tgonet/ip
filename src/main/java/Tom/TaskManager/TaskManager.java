package Tom.TaskManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import Tom.Exception.TomException;
import Tom.FileManager.FileManager;
import Tom.Task.Deadline;
import Tom.Task.Events;
import Tom.Task.Task;
import Tom.Task.ToDo;
import Tom.UI.UI;

/**
 * Manages a list of {@link Task} objects and provides methods for adding,
 * removing,
 * viewing, marking/unmarking tasks, and checking tasks by specific dates.
 * <p>
 * This class interacts with a {@link FileManager} to persist tasks to a file
 * and
 * supports {@link ToDo}, {@link Deadline}, and {@link Events} task types.
 * </p>
 */

public class TaskManager {
    private ArrayList<Task> tasks;
    private UI ui;

    public TaskManager(ArrayList<Task> taskList, UI ui) {
        this.tasks = taskList;
        this.ui = ui;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Size of the task list.
     */

    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Prints tasks occurring at a specified date and time.
     *
     * @param input Input string containing the date and time, e.g., "occur
     *              2025-08-30 14:00".
     */

    public String checkOccuringDates(String input) throws TomException {
        ArrayList<Task> temp = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            String[] s = input.split(" ");
            String dateTime = s[1] + " " + s[2];
            LocalDateTime tempDateTime = LocalDateTime.parse(dateTime, fmt);
            this.tasks.forEach(t -> {
                if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    if (d.getDeadline().equals(tempDateTime)) {
                        temp.add(d);
                    }
                } else if (t instanceof Events) {
                    Events e = (Events) t;
                    if (tempDateTime.isAfter(e.getStart()) && tempDateTime.isBefore(e.getEnd())) {
                        temp.add(e);
                    }
                }
            });
            if (temp.size() > 0) {
                String result = "On " + dateTime + " you have these activities\n:";
                for (Task g : temp) {
                    result += g.toString() + "\n";
                }
                return result;
            }
            else {
                return "You have no activity on this day";
            }
        } catch (DateTimeParseException e) {
            throw new TomException(
                    "Please enter in this format \"occur [yyyy-MM-dd HH:mm]\"");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TomException(
                    "Please enter in this format \"occur [yyyy-MM-dd HH:mm]\"");
        }
    }

    /**
     * Removes a task from the list by its index and updates the file.
     *
     * @param input       Input string containing the task index to remove.
     * @param fileManager FileManager used to update the file.
     * @return The task removed, or null if removal failed.
     * @throws TomException If the task index is invalid.
     */

    public Task removeTask(String input, FileManager fileManager) throws TomException {
        Task t = null;
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index >= 0 && index < this.tasks.size()) {
                t = this.tasks.remove(index);
                fileManager.writeToFile(this.tasks);
                return t;
            } else {
                throw new TomException("This is an invalid task. Please check the task number and try again.");
            }
        } catch (IOException e) {
            throw new TomException(e.getMessage());
        }
    }

    /**
     * Adds a new task to the list based on input and updates the file.
     *
     * @param input       Input string describing the task to add.
     * @param fileManager FileManager used to append the new task.
     * @return The newly added task, or null if addition failed.
     * @throws TomException If the input format is invalid or constraints are
     *                      violated.
     */

    public Task addTask(String input, FileManager fileManager) throws TomException {
        String task = input.split(" ")[0];
        String[] val;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        assert getSize() >= 0 : "Task list size should never be negative";
        if (getSize() < 100) {
            try {
                switch (task) {
                case "deadline":
                    if (!input.contains("/by")) {
                        throw new TomException(
                                "Please enter in this format \"deadline [description] /by [yyyy-MM-dd HH:mm] \"");
                    }
                    val = input.substring(9).trim().split("/by", 2);
                    if (val[0].isBlank() || val[1].isBlank()) {
                        throw new TomException(
                                "Please enter in this format \"deadline [description] /by [yyyy-MM-dd HH:mm] \"");
                    }
                    Deadline d = new Deadline(val[0].trim(), LocalDateTime.parse(val[1].trim(), fmt));
                    this.tasks.add(d);
                    fileManager.appendToFile(String.format("%s\n", d.toFileString()));
                    return d;

                case "todo":
                    String description = input.substring(4).trim();
                    if (description.isBlank()) {
                        throw new TomException(
                                "Please enter in this format \"todo [description]\"");
                    }
                    ToDo t = new ToDo(description);
                    this.tasks.add(t);
                    fileManager.appendToFile(String.format("%s\n", t.toFileString()));
                    return t;

                case "event":
                    val = input.substring(6).trim().split("/from", 2);
                    if (!input.contains("/from") || !input.contains("/to")) {
                        throw new TomException(
                                "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm]"
                                        + " /to [yyyy-MM-dd HH:mm] \"");
                    }
                    String[] val2 = val[1].split("/to", 2);
                    if (val[0].isBlank() || val2[0].isBlank() || val2[1].isBlank()) {
                        throw new TomException(
                                "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm]"
                                        + " /to [yyyy-MM-dd HH:mm] \"");
                    }
                    Events e = new Events(val[0].trim(), LocalDateTime.parse(val2[0].trim(), fmt),
                            LocalDateTime.parse(val2[1].trim(), fmt));
                    this.tasks.add(e);
                    fileManager.appendToFile(String.format("%s\n", e.toFileString()));
                    return e;

                default:
                    throw new TomException("Please enter something that is under my control");
                }
            } catch (IOException e) {
                throw new TomException(e.getMessage() + "Please try again");
            } catch (DateTimeParseException e) {
                throw new TomException("Please follow the format yyyy-MM-dd HH:mm");
            }
        }
        return null;
    }

    /**
     * Displays the current list of tasks to the console.
     */

    public String viewList() {
        int count = 1;
        String result;
        if (this.tasks.size() > 0) {
            result = "Here are the tasks in your list: \n";
            for (Task i : this.tasks) {
                result += count + ". " + i.toString() + "\n";
                count++;
            }
            return result;
        } else {
            assert getSize() == 0 : "Task list size should be zero";
            return "You have not added anything to the list!";
        }
    }

    /**
     * Marks or unmarks a task as complete and updates the file.
     *
     * @param input       Input string containing the task index.
     * @param isMarking   True to mark, false to unmark.
     * @param fileManager FileManager used to persist changes.
     * @return The updated task, or null if the operation failed.
     * @throws TomException If the task index is invalid or task is already in the
     *                      requested state.
     */

    public Task mark(String input, boolean isMarking, FileManager fileManager) throws TomException {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        if (index >= 0 && index < getSize()) {
            Task task = this.tasks.get(index);
            if (isMarking) {
                if (!task.getIsMarked()) {
                    task.mark();
                    try {
                        fileManager.writeToFile(this.tasks);
                        return task;
                    } catch (IOException e) {
                        throw new TomException(e.getMessage() + "Please try again");
                    }
                } else {
                    throw new TomException("Task is already marked.");
                }
            } else {
                if (task.getIsMarked()) {
                    task.mark();
                    try {
                        fileManager.writeToFile(this.tasks);
                        return task;
                    } catch (IOException e) {
                        throw new TomException(e.getMessage() + "Please try again");
                    }
                } else {
                    throw new TomException("Task is already unmarked.");
                }
            }
        } else {
            throw new TomException("Invalid Task. Please check the task number and try again.");
        }
    }

    /**
     * Prints tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param input Keyword to search for in task descriptions.
     */

    public String findSimilarDescriptions(String input) {
        String desc = input.split(" ")[1];
        ArrayList<Task> matchedTasks = new ArrayList<>();
        String lowerKeyword = desc.toLowerCase();

        for (Task t : this.tasks) {
            if (t.getName().toLowerCase().contains(lowerKeyword)) {
                matchedTasks.add(t);
            }
        }

        if (!matchedTasks.isEmpty()) {
            assert matchedTasks.size() >= 0 : "Matched Task list size should not be negative";
            String result = "";
            result = "Tasks matching \"" + desc + "\":\n";
            for (Task task : matchedTasks) {
                result += task.toString() + "\n";
            }
            return result;
        } else {
            assert matchedTasks.size() == 0 : "Matched Task list size should be negative";
            return "No tasks found matching \"" + desc + "\".";
        }
    }

}
