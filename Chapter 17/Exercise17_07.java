import java.io.*;

public class Exercise17_07 {
    public static void main(String[] args) {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan(1.8, 10, 10000);

        try (
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Exercise17_07.dat"))
        ) {
            output.writeObject(loan1);
            output.writeObject(loan2);
        } catch (IOException ex) {
            System.out.println("File could not be opened");
        }

        // Call the method to read and output loan data
        outputData();
    }

    // Method to read Loan objects and print total loan amount
    public static void outputData() {
        double totalLoanAmount = 0;

        try (
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("Exercise17_07.dat"))
        ) {
            while (true) {
                Loan loan = (Loan) input.readObject();
                totalLoanAmount += loan.getLoanAmount();
            }
        } catch (EOFException eof) {
            // Reached end of file
            System.out.println("Total loan amount from file: $" + totalLoanAmount);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error reading loan data: " + ex.getMessage());
        }
    }
}
