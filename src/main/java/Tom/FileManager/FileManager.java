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

public class FileManager {
    String filePath;
    File f;

    public FileManager(String filePath) {
        this.filePath = filePath;
        this.f = new File(filePath);
        init();
    }

    public void init() {
        if (!this.f.exists()) {
            this.f.getParentFile().mkdirs(); // make parent folders if needed
            try {
                this.f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // create the file
        }
    }

    public void writeToFile(ArrayList<Task> ls) throws IOException {
        FileWriter fw = new FileWriter(this.filePath);
        for (int i = 0; i < ls.size(); i++) {
            fw.write(ls.get(i).toFileString() + System.lineSeparator());
        }
        fw.close();
    }

    public ArrayList<Task> getFileContents() throws FileNotFoundException {
        File f = new File(this.filePath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
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

    public void appendToFile(String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(this.filePath, true); // create a FileWriter in append mode
        fw.write(textToAppend);
        fw.close();
    }
}
