public class Deadline extends Task {
    String deadline;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[" + "D" + "]" + super.toString() + " (by: " + this.deadline + ")";
    }

    public String toFileString() {
        return "D" + "," + super.toFileString() + "," + this.deadline;
    }

    public static Deadline fromFileString(String[] fileString) {
        Deadline d = new Deadline(fileString[2], fileString[3]);
        if (fileString[1].equals("X")) {
            d.mark();
        }
        return d;
    }
}
