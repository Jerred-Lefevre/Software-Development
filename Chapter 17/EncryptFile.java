import java.io.*;
import java.util.Scanner;

public class EncryptFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter input file name: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter output file name: ");
        String outputFile = scanner.nextLine();

        try (
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
        ) {
            int byteRead;
            while ((byteRead = fis.read()) != -1) {
                int encryptedByte = (byteRead + 5) & 0xFF;
                fos.write(encryptedByte);
            }
            System.out.println("File encrypted successfully.");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
