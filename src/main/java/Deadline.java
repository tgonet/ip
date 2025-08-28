import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDateTime deadline;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    public Deadline(String name, LocalDateTime deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[" + "D" + "]" + super.toString() + " (by: " + this.deadline.format(formatter) + ")";
    }

    public String toFileString() {
        return "D" + "," + super.toFileString() + "," + this.deadline.format(formatter);
    }

    public static Deadline fromFileString(String[] fileString) {
        Deadline d = new Deadline(fileString[2], LocalDateTime.parse(fileString[3], formatter));
        if (fileString[1].equals("X")) {
            d.mark();
        }
        return d;
    }
}
