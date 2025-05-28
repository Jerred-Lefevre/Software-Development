import java.util.Random;
import java.util.Scanner;

public class ArrayIndexCheck {
    public static void main(String[] args) {
        // Create an array with 100 random integers
        int[] numbers = new int[100];
        Random rand = new Random();

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(1000); // Random integer between 0 and 999
        }

        // Prompt user to enter an index
        Scanner input = new Scanner(System.in);
        System.out.print("Enter an index (0-99): ");

        try {
            int index = input.nextInt();
            System.out.println("Value at index " + index + " is " + numbers[index]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Out of Bounds");
        } catch (Exception e) {
            System.out.println("Invalid input");
        }

        input.close();
    }
}
