import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class BattleshipLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowMenu();
        });
    }

    private static void createAndShowMenu() {
        // Create window frame
        JFrame frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Center on screen

        // Create panel and layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title label
        JLabel title = new JLabel("Battleship", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Buttons for game modes
        JButton vsCpuButton = new JButton("VS CPU Player");
        JButton twoPlayerButton = new JButton("2-Player Mode");

        // Add components to panel
        panel.add(title);
        panel.add(vsCpuButton);
        panel.add(twoPlayerButton);

        // Add panel to frame and display
        frame.add(panel);
        frame.setVisible(true);

        // Button: Play vs AI
        vsCpuButton.addActionListener(e -> {
            frame.dispose(); // Close menu window
            Scanner scanner = new Scanner(System.in);
            Main.runPvAIMode(scanner); // Call method from Main.java
        });

        // Button: 2-Player mode
        twoPlayerButton.addActionListener(e -> {
            frame.dispose(); // Close menu window
            Scanner scanner = new Scanner(System.in);
            Main.runPvPMode(scanner); // Call method from Main.java
        });
    }
}
