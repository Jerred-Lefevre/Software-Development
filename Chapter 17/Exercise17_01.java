import java.io.*;
import java.util.*;

public class Exercise17_01 {
    public static void main(String[] args) {
        String filename = "Exercise17_01.txt";

        try (
            // Use FileWriter in append mode
            PrintWriter output = new PrintWriter(new FileWriter(filename, true))
        ) {
            Random rand = new Random();

            // Write 100 random integers separated by spaces
            for (int i = 0; i < 100; i++) {
                int number = rand.nextInt(100); // 0 to 99, change range if needed
                output.print(number + " ");
            }

            System.out.println("100 random integers written to " + filename);
        } catch (IOException ex) {
            System.out.println("An error occurred: " + ex.getMessage());
        }
    }
}
