package Tom.Task;

public class Task {
    private String name;
    private Boolean isMarked;

    public Task(String name) {
        this.name = name;
        this.isMarked = false;
    }

    public void mark() {
        this.isMarked = !this.isMarked;
    }

    public Boolean getIsMarked() {
        return this.isMarked;
    }

    @Override
    public String toString() {
        String val = this.isMarked ? "X" : " ";
        return "[" + val + "] " + this.name;
    }

    public String toFileString() {
        String val = this.isMarked ? "X" : " ";
        return val + "," + this.name;
    }
}
