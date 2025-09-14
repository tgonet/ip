package tom.taskmanager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import tom.exception.TomException;
import tom.filemanager.FileManager;
import tom.parser.Parser;
import tom.task.Deadline;
import tom.task.Events;
import tom.task.Task;
import tom.task.ToDo;

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
    private int MAX_SIZE = 100;
    private DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public TaskManager(ArrayList<Task> taskList) {
        this.tasks = taskList;
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
     * Checks for tasks occurring on the specified date and time based on the user input.
     * <p>
     * This method parses the input string into a {@link LocalDateTime}, retrieves all
     * tasks scheduled on or overlapping with that date and time, and returns a formatted
     * string describing the activities. If no tasks are found, a message stating there
     * are no activities is returned.
     * </p>
     *
     * @param input The user input containing the date and time to check, in the format "occur [yyyy-MM-dd HH:mm]".
     * @return A formatted string listing all activities occurring at the specified date and time,
     *         or a message indicating no activities exist on that day.
     * @throws TomException If the input cannot be parsed into a valid date and time
     *                      (wrong format or missing components).
     */

    public String checkOccuringDates(String input) throws TomException {

        try {
            LocalDateTime tempDateTime = Parser.parseCheckRecurringDates(input, FMT);
            ArrayList<Task> temp = getTasksWithinDate(tempDateTime);

            if (temp.size() > 0) {
                String result = "On " + tempDateTime.format(FMT) + " you have these activities\n:";
                for (Task g : temp) {
                    result += g.toString() + "\n";
                }
                return result;
            } else {
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
     * Retrieves all tasks scheduled or ongoing at the specified date and time.
     * <p>
     * This includes:
     * <ul>
     *     <li>Deadlines whose due date equals the specified date and time.</li>
     *     <li>Events whose start and end times enclose the specified date and time.</li>
     * </ul>
     * </p>
     *
     * @param tempDateTime The date and time to check for tasks.
     * @return An {@link ArrayList} of {@link Task} objects occurring at the specified time.
     */

    public ArrayList<Task> getTasksWithinDate(LocalDateTime tempDateTime) {
        ArrayList<Task> temp = new ArrayList<>();

        this.tasks.forEach(t -> {
                boolean isADeadline = t instanceof Deadline;
                boolean isAEvent = t instanceof Events;

                if (isADeadline) {
                    Deadline d = (Deadline) t;
                    if (d.getDeadline().equals(tempDateTime)) {
                        temp.add(d);
                    }
                } else if (isAEvent) {
                    Events e = (Events) t;
                    boolean isWithinEvent = tempDateTime.isAfter(e.getStart()) && tempDateTime.isBefore(e.getEnd());
                    if (isWithinEvent) {
                        temp.add(e);
                    }
                }

            });

            return temp;
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
            boolean isValidIndex = index >= 0 && index < getSize();

            if (isValidIndex) {
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

    public Task addTaskProcessor(String input, FileManager fileManager) throws TomException {
        String task = input.split(" ")[0];
        assert getSize() >= 0 : "Task list size should never be negative";

        if (getSize() >= MAX_SIZE) {
            throw new TomException("Task limit reached. Cannot add more than 100 tasks.");
        }

        try {
            switch (task) {
            case "deadline":
                Deadline d = Parser.parseForDeadlineTask(input, FMT);

                isDuplicate(d);
                addTask(d, fileManager);
                return d;

            case "todo":
                ToDo t = Parser.parseForToDoTask(input);

                isDuplicate(t);
                addTask(t, fileManager);
                return t;

            case "event":
                Events e = Parser.parseForEventTask(input, FMT);

                isDuplicate(e);
                addTask(e, fileManager);
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

    public void addTask(Task t, FileManager fileManager) throws IOException {
        this.tasks.add(t);
        fileManager.appendToFile(String.format("%s\n", t.toFileString()));
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
        boolean isValidIndex = index >= 0 && index < getSize();

        if (!isValidIndex) {
            throw new TomException("Invalid Task. Please check the task number and try again.");
        }

        Task task = this.tasks.get(index);
        if (isMarking) {
            if (task.getIsMarked()) {
                throw new TomException("Task is already marked.");
            }

            task.mark();
            try {
                fileManager.writeToFile(this.tasks);
                return task;
            } catch (IOException e) {
                throw new TomException(e.getMessage() + "Please try again");
            }
        } else {
            if (!task.getIsMarked()) {
                throw new TomException("Task is already unmarked.");
            }

            task.mark();
            try {
                fileManager.writeToFile(this.tasks);
                return task;
            } catch (IOException e) {
                throw new TomException(e.getMessage() + "Please try again");
            }
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

        if (matchedTasks.isEmpty()) {
            return "No tasks found matching \"" + desc + "\".";
        }

        assert matchedTasks.size() > 0 : "Matched tasks should not be empty here";
        String result = "";
        result = "Tasks matching \"" + desc + "\":\n";
        for (Task task : matchedTasks) {
            result += task.toString() + "\n";
        }
        return result;
    }

    /**
     * Checks whether the specified task already exists in the task list.
     * <p>
     * This method iterates over all existing tasks and compares each one with the
     * provided task using {@link Task#equals(Object)}. If a duplicate is found, a
     * {@link TomException} is thrown to prevent adding it.
     * </p>
     *
     * @param newTask The new {@link Task} to be checked against existing tasks.
     * @throws TomException If the specified task already exists in the list.
     */

    public void isDuplicate(Task newTask) throws TomException {
        for (Task existingTask : this.tasks) {
            if (existingTask.equals(newTask)) {
                throw new TomException(
                            "This task already exists in your list. Duplicate tasks are not allowed.");
            }
        }
    }

}
