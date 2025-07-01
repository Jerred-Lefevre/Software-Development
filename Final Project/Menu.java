import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Battleship Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Battleship", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");

        startButton.setPreferredSize(new Dimension(150, 40));
        exitButton.setPreferredSize(new Dimension(150, 40));

        startButton.addActionListener((ActionEvent e) -> {
            launchGame();
            dispose(); // close the menu window
        });

        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void launchGame() {
        Board playerBoard = new Board();
        AIPlayer ai = new AIPlayer();
        Board aiBoard = ai.getBoard();

        // Manually place player ships
        playerBoard.placeShip(new Ship("Carrier", 4, 0, 0, true));
        playerBoard.placeShip(new Ship("Destroyer", 3, 1, 0, true));
        playerBoard.placeShip(new Ship("Patrol Boat", 2, 2, 0, true));

        // Randomly place AI ships
        Ship[] ships = {
            new Ship("Carrier", 4, 0, 0, true),
            new Ship("Destroyer", 3, 0, 0, true),
            new Ship("Patrol Boat", 2, 0, 0, true)
        };

        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = (int) (Math.random() * 6);
                int col = (int) (Math.random() * 6);
                boolean horizontal = Math.random() < 0.5;
                Ship attempt = new Ship(ship.getName(), ship.getSize(), row, col, horizontal);
                placed = aiBoard.placeShip(attempt);
            }
        }

        new BattleshipGameGUI(playerBoard, aiBoard, ai);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }
}
