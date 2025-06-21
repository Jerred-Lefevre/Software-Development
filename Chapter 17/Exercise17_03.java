import java.io.*;
import java.util.Random;

public class Exercise17_03 {

    public static void main(String[] args) {
        String filename = "Exercise17_03.dat";

        // Write 100 random integers to the binary file
        writeIntegersToFile(filename);

        // Read and sum the integers from the binary file
        int sum = readAndSumIntegers(filename);
        System.out.println("The sum of the integers is: " + sum);
    }

    // Method 1: Write 100 random integers to binary file using writeInt()
    public static void writeIntegersToFile(String filename) {
        Random rand = new Random();

        try (
            // true enables append mode
            DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(
                    new FileOutputStream(filename, true)
                )
            )
        ) {
            for (int i = 0; i < 100; i++) {
                int number = rand.nextInt(100); // 0–99, adjust if needed
                output.writeInt(number);
            }
            System.out.println("100 random integers written to " + filename);
        } catch (IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }

    // Method 2: Read integers from file and compute the sum
    public static int readAndSumIntegers(String filename) {
        int sum = 0;

        try (
            DataInputStream input = new DataInputStream(
                new BufferedInputStream(
                    new FileInputStream(filename)
                )
            )
        ) {
            // Read until end of file
            while (true) {
                int number = input.readInt();
                sum += number;
            }
        } catch (EOFException eof) {
            // End of file reached
        } catch (IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
        }

        return sum;
    }
}
