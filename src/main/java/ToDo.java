public class ToDo extends Task {

    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[" + "T" + "]" + super.toString();
    }

    public String toFileString() {
        return "T" + "," + super.toFileString();
    }

    public static ToDo fromFileString(String[] fileString) {
        ToDo t = new ToDo(fileString[2]);
        if (fileString[1].equals("X")) {
            t.mark();
        }
        return t;
    }

}
