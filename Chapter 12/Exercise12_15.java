import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class Exercise12_15 {
    public static void main(String[] args) throws IOException {
        File file = new File("Exercise12_15.txt");

        // Create file and write 100 random integers if file doesn't exist
        if (!file.exists()) {
            PrintWriter output = new PrintWriter(file);
            Random rand = new Random();
            for (int i = 0; i < 100; i++) {
                output.print(rand.nextInt(1000) + " ");
            }
            output.close();
        }

        // Read integers from file
        Scanner input = new Scanner(file);
        List<Integer> numbers = new ArrayList<>();
        while (input.hasNextInt()) {
            numbers.add(input.nextInt());
        }
        input.close();

        // Sort and display numbers
        Collections.sort(numbers);
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }
}
