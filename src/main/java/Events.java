public class Events extends Task {
    String start;
    String end;

    public Events(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[" + "E" + "]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }

    public String toFileString() {
        return "E" + "," + super.toFileString() + "," + this.start + "," + this.end;
    }

    public static Events fromFileString(String[] fileString) {
        Events e = new Events(fileString[2], fileString[3], fileString[4]);
        if (fileString[1].equals("X")) {
            e.mark();
        }
        return e;
    }
}
