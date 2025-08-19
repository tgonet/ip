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

    public Boolean getMarked() {
        return this.marked;
    }

    @Override
    public String toString() {
        String val = this.marked ? "X" : " ";
        return "[" + val + "] " + this.name;
    }
}
