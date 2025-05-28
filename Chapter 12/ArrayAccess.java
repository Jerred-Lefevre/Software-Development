import java.util.Scanner;
import java.util.Random;

public class ArrayAccess {
    public static void main(String[] args) {
        int[] numbers = new int[100];
        Random rand = new Random();

        // Fill the array with 100 random integers between 0 and 999
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(1000);
        }

        Scanner input = new Scanner(System.in);
        System.out.print("Enter an index between 0 and 99: ");
        int index = input.nextInt();

        try {
            // Try to access the array at the entered index
            System.out.println("Value at index " + index + ": " + numbers[index]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // If index is out of bounds, handle the error
            System.out.println("Out of Bounds");
        }

        input.close();
    }
}
