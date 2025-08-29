import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> taskList;

    TaskManager(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public int getSize() {
        return this.taskList.size();
    }

    public void checkOccuringDates(String input) {
        ArrayList<Task> temp = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String[] s = input.split(" ");
        String dateTime = s[1] + " " + s[2];
        LocalDateTime tempDateTime = LocalDateTime.parse(dateTime, fmt);
        this.taskList.forEach(t -> {
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
            System.out.println("On " + dateTime + " you have these activities:");
            for (Task g : temp) {
                System.out.println(g.toString());
            }
        }
    }

    public Task removeTask(String input, FileManager fileManager) throws TomException {
        Task t = null;
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index >= 0 && index < this.taskList.size()) {
                t = this.taskList.remove(index);
                fileManager.writeToFile(this.taskList);
                return t;
            } else {
                throw new TomException("This is an invalid task. Please check the task number and try again.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return t;
    }

    public Task addTask(String input, FileManager fileManager) throws TomException {
        String task = input.split(" ")[0];
        String[] val;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String description;

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
                        this.taskList.add(d);
                        fileManager.appendToFile(String.format("%s\n", d.toFileString()));
                        return d;

                    case "todo":
                        description = input.substring(4).trim();
                        if (description.isBlank()) {
                            throw new TomException(
                                    "Please enter in this format \"todo [description]\"");
                        }
                        ToDo t = new ToDo(description);
                        this.taskList.add(t);
                        fileManager.appendToFile(String.format("%s\n", t.toFileString()));
                        return t;

                    case "event":
                        val = input.substring(6).trim().split("/from", 2);
                        if (!input.contains("/from") || !input.contains("/to")) {
                            throw new TomException(
                                    "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm] /to [yyyy-MM-dd HH:mm] \"");
                        }
                        String[] val2 = val[1].split("/to", 2);
                        if (val[0].isBlank() || val2[0].isBlank() || val2[1].isBlank()) {
                            throw new TomException(
                                    "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm] /to [yyyy-MM-dd HH:mm] \"");
                        }
                        Events e = new Events(val[0].trim(), LocalDateTime.parse(val2[0].trim(), fmt),
                                LocalDateTime.parse(val2[1].trim(), fmt));
                        this.taskList.add(e);
                        fileManager.appendToFile(String.format("%s\n", e.toFileString()));
                        return e;

                    default:
                        throw new TomException("Please enter something that is under my control");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage() + "Please try again");
            } catch (DateTimeParseException e) {
                throw new TomException("Please follow the format yyyy-MM-dd HH:mm");
            }
        }
        return null;
    }

    public void viewList() {
        int count = 1;
        if (this.taskList.size() > 0) {
            System.out.println("____________________________________________________________");
            System.out.println("Here are the tasks in your list:");
            for (Task i : this.taskList) {
                System.out.println(count + ". " + i.toString());
                count++;
            }
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println("You have not added anything to the list!");
            System.out.println("____________________________________________________________");
        }
    }

    public Task mark(String input, boolean isMarking, FileManager fileManager) throws TomException {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        if (index >= 0 && index < getSize()) {
            Task task = this.taskList.get(index);
            if (isMarking) {
                if (!task.getMarked()) {
                    task.mark();
                    try {
                        fileManager.writeToFile(this.taskList);
                        return task;
                    } catch (IOException e) {
                        System.out.println(e.getMessage() + "Please try again");
                    }
                } else {
                    throw new TomException("Task is already marked.");
                }
            } else {
                if (task.getMarked()) {
                    task.mark();
                    try {
                        fileManager.writeToFile(this.taskList);
                        return task;
                    } catch (IOException e) {
                        System.out.println(e.getMessage() + "Please try again");
                    }
                } else {
                    throw new TomException("Task is already unmarked.");
                }
            }
        } else {
            throw new TomException("Invalid Task. Please check the task number and try again.");
        }
        return null;
    }

}
