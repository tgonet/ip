package Tom.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Tom.Task.Deadline;
import Tom.Task.Events;
import Tom.Task.Task;
import Tom.Task.ToDo;

/**
 * Handles reading from and writing to a file for storing {@link Task} objects.
 * <p>
 * The {@code FileManager} class manages a file at the specified path. It
 * ensures
 * that the file exists upon initialization, provides methods to write a list of
 * tasks to the file, append text to the file, and read the file contents into
 * a list of {@link Task} objects.
 * </p>
 */

public class FileManager {
    String filePath;
    File f;

    public FileManager(String filePath) {
        this.filePath = filePath;
        this.f = new File(filePath);
        init();
    }

    /**
     * Initializes the file at the specified path.
     * Creates parent directories and the file if they do not exist.
     */

    public void init() {
        if (!this.f.exists()) {
            this.f.getParentFile().mkdirs(); // make parent folders if needed
            try {
                this.f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    }

    /**
     * Writes a list of tasks to the file, overwriting existing content.
     *
     * @param ls List of tasks to write.
     * @throws IOException If an I/O error occurs while writing.
     */

    public void writeToFile(ArrayList<Task> ls) throws IOException {
        FileWriter fw = new FileWriter(this.filePath);
        for (int i = 0; i < ls.size(); i++) {
            fw.write(ls.get(i).toFileString() + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Reads the file contents and returns them as a list of tasks.
     *
     * @return List of tasks read from the file.
     * @throws FileNotFoundException If the file does not exist.
     */

    public ArrayList<Task> getFileContents() throws FileNotFoundException {
        File f = new File(this.filePath); 
        Scanner s = new Scanner(f); 
        ArrayList<Task> ls = new ArrayList<Task>();
        while (s.hasNext()) {
            String[] str = s.nextLine().split(",");
            switch (str[0]) {
            case "T":
                ls.add(ToDo.fromFileString(str));
                break;
            case "D":
                ls.add(Deadline.fromFileString(str));
                break;
            case "E":
                ls.add(Events.fromFileString(str));
                break;
            default:
                break;
            }

        }
        s.close();
        return ls;
    }

    /**
     * Appends text to the end of the file without overwriting existing content.
     *
     * @param textToAppend Text to append to the file.
     * @throws IOException If an I/O error occurs while writing.
     */

    public void appendToFile(String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(this.filePath, true);
        fw.write(textToAppend);
        fw.close();
    }
}
