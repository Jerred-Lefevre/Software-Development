import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class IO_Sorting {
    public static void main(String[] args) throws IOException {
        File file = new File("Exercise12_15.txt");

        // Step 1: Write to the file (create if it doesn't exist)
        if (!file.exists()) {
            PrintWriter output = new PrintWriter(file);
            Random rand = new Random();
            for (int i = 0; i < 100; i++) {
                int num = rand.nextInt(1001); // Random integer between 0 and 1000
                output.print(num + " ");
            }
            output.close();
        }

        // Step 2: Read the file and store numbers
        Scanner input = new Scanner(file);
        List<Integer> numbers = new ArrayList<>();

        while (input.hasNextInt()) {
            numbers.add(input.nextInt());
        }
        input.close();

        // Step 3: Sort the numbers
        Collections.sort(numbers);

        // Step 4: Display the sorted numbers
        System.out.println("Sorted numbers:");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }
}
