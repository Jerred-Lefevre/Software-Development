import java.io.*;
import java.util.Scanner;

public class DecryptFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter encrypted input file name: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter output file name for decrypted file: ");
        String outputFile = scanner.nextLine();

        try (
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
        ) {
            int byteRead;
            while ((byteRead = fis.read()) != -1) {
                int decryptedByte = (byteRead - 5) & 0xFF;
                fos.write(decryptedByte);
            }
            System.out.println("File decrypted successfully.");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
