public class Task {
    private String name;
    private Boolean marked;

    public Task(String name) {
        this.name = name;
        this.marked = false;
    }

    public void mark() {
        this.marked = !this.marked;
    }

    @Override
    public String toString() {
        return "[" + (this.marked ? "X" : "") + "]" + this.name;
    }
}
