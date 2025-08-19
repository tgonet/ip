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
        return "[" + "E " + "]" + this.toString() + "(from: " + this.start + "to:" + this.end + ")";
    }
}
