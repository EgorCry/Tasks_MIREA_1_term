package Tasks_31102023.text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Task_1 {
    public static void main(String[] args) {
        String filepath = "src/Tasks_31102023/text/text.txt";

        try {
            List<String> lines = Files.readAllLines(Paths.get(filepath));

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
