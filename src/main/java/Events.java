import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    LocalDateTime start;
    LocalDateTime end;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    public Events(String name, LocalDateTime start, LocalDateTime end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[" + "E" + "]" + super.toString() + " (from: "
                + this.start.format(formatter) + " to: "
                + this.end.format(formatter) + ")";
    }

    public String toFileString() {
        return "E" + "," + super.toFileString() + "," + this.start.format(formatter) + "," + this.end.format(formatter);
    }

    public static Events fromFileString(String[] fileString) {
        Events e = new Events(fileString[2], LocalDateTime.parse(fileString[3], formatter), LocalDateTime.parse(fileString[4], formatter));
        if (fileString[1].equals("X")) {
            e.mark();
        }
        return e;
    }
}
