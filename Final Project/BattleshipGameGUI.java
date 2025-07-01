import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class BattleshipGameGUI extends JFrame {
    private static final int SIZE = 6;

    private Board playerBoard;
    private Board aiBoard;
    private AIPlayer ai;

    private JPanel playerPanel;
    private JPanel aiPanel;
    private JLabel statusLabel;

    // For manual ship placement
    private Ship[] shipsToPlace = {
            new Ship("Carrier", 4, 0, 0, true),
            new Ship("Destroyer", 3, 0, 0, true),
            new Ship("Patrol Boat", 2, 0, 0, true)
    };
    private int currentShipIndex = 0;
    private boolean placingShips = true;
    private boolean horizontalPlacement = true;
    private JButton orientationToggleBtn;

    public BattleshipGameGUI(Board playerBoard, Board aiBoard, AIPlayer ai) {
        this.playerBoard = playerBoard;
        this.aiBoard = aiBoard;
        this.ai = ai;

        setupUI();
    }

    private void setupUI() {
        setTitle("Battleship Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Place your " + shipsToPlace[currentShipIndex].getName() +
                " (size " + shipsToPlace[currentShipIndex].getSize() + ") - Click to place", SwingConstants.CENTER);
        statusLabel.setForeground(new Color(0x00008B)); // dark blue
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);

        // Orientation toggle button
        orientationToggleBtn = new JButton("Orientation: Horizontal");
        orientationToggleBtn.setFocusable(false);
        orientationToggleBtn.addActionListener(e -> {
            horizontalPlacement = !horizontalPlacement;
            orientationToggleBtn.setText("Orientation: " + (horizontalPlacement ? "Horizontal" : "Vertical"));
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(statusLabel, BorderLayout.CENTER);
        topPanel.add(orientationToggleBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        playerPanel = createBoardPanel(playerBoard, false);
        aiPanel = createBoardPanel(aiBoard, true);

        JPanel boardsPanel = new JPanel(new GridLayout(1, 2));
        boardsPanel.add(playerPanel);
        boardsPanel.add(aiPanel);

        add(boardsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createBoardPanel(Board board, boolean isAI) {
        JPanel panel = new JPanel(new GridLayout(SIZE + 1, SIZE + 1));
        TitledBorder border = BorderFactory.createTitledBorder(isAI ? "AI Board" : "Your Board");
        border.setTitleColor(Color.WHITE);  // white titles
        panel.setBorder(border);

        // Headers
        panel.add(new JLabel(""));
        for (int col = 1; col <= SIZE; col++) {
            JLabel header = new JLabel(String.valueOf(col), SwingConstants.CENTER);
            header.setForeground(Color.WHITE);
            panel.add(header);
        }

        for (int row = 0; row < SIZE; row++) {
            JLabel header = new JLabel(String.valueOf((char) ('A' + row)), SwingConstants.CENTER);
            header.setForeground(Color.WHITE);
            panel.add(header);
            for (int col = 0; col < SIZE; col++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(40, 40));
                btn.setMargin(new Insets(0, 0, 0, 0));
                btn.setFocusable(false);
                btn.setBackground(new Color(0x00008B)); // water = dark blue
                btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                if (isAI) {
                    int r = row;
                    int c = col;
                    btn.addActionListener(e -> {
                        if (!placingShips) {
                            playerShoots(r, c, btn);
                        }
                    });
                } else {
                    // For manual ship placement on player board, listen for clicks
                    int r = row;
                    int c = col;
                    btn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (placingShips) {
                                placePlayerShip(r, c);
                            }
                        }
                    });
                }

                panel.add(btn);
            }
        }

        panel.setBackground(Color.BLACK);
        return panel;
    }

    private void placePlayerShip(int row, int col) {
        Ship shipToPlace = shipsToPlace[currentShipIndex];
        Ship newShip = new Ship(shipToPlace.getName(), shipToPlace.getSize(), row, col, horizontalPlacement);

        if (playerBoard.placeShip(newShip)) {
            // Mark ship visually on player's board
            markShipOnPlayerBoard(newShip);

            currentShipIndex++;
            if (currentShipIndex < shipsToPlace.length) {
                Ship nextShip = shipsToPlace[currentShipIndex];
                statusLabel.setText("Place your " + nextShip.getName() + " (size " + nextShip.getSize() + ") - Click to place");
            } else {
                // Finished placing ships
                placingShips = false;
                statusLabel.setText("All ships placed! Your turn.");
                orientationToggleBtn.setEnabled(false);

                // Now place AI ships randomly
                placeAIShips();

                // Enable AI board buttons for shooting
                enableAIBoardButtons(true);
            }
        } else {
            statusLabel.setText("Invalid placement! Try again.");
        }
    }

    private void markShipOnPlayerBoard(Ship ship) {
        int startRow = ship.getStartRow();
        int startCol = ship.getStartCol();
        boolean horizontal = ship.isHorizontal();
        int size = ship.getSize();

        for (int i = 0; i < size; i++) {
            int r = startRow + (horizontal ? 0 : i);
            int c = startCol + (horizontal ? i : 0);
            int index = (r + 1) * (SIZE + 1) + (c + 1);
            Component comp = playerPanel.getComponent(index);
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setBackground(new Color(0x00CED1)); // Dark Turquoise to show ship placement
            }
        }
    }

    private void placeAIShips() {
        Ship[] ships = {
                new Ship("Carrier", 4, 0, 0, true),
                new Ship("Destroyer", 3, 0, 0, true),
                new Ship("Patrol Boat", 2, 0, 0, true)
        };

        Random rand = new Random();
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = rand.nextInt(SIZE);
                int col = rand.nextInt(SIZE);
                boolean horizontal = rand.nextBoolean();
                Ship attempt = new Ship(ship.getName(), ship.getSize(), row, col, horizontal);
                placed = aiBoard.placeShip(attempt);
            }
        }
    }

    private void enableAIBoardButtons(boolean enable) {
        // AI panel grid has headers, buttons start at index SIZE + 1 for rows, and first column is headers
        int totalComponents = aiPanel.getComponentCount();
        for (int i = 0; i < totalComponents; i++) {
            Component comp = aiPanel.getComponent(i);
            if (comp instanceof JButton) {
                comp.setEnabled(enable);
            }
        }
    }

    private void playerShoots(int row, int col, JButton button) {
        if (!aiBoard.canShootAt(row, col)) {
            statusLabel.setText("Already shot there! Your turn.");
            statusLabel.setForeground(new Color(0x00008B)); // dark blue
            return;
        }

        boolean hit = aiBoard.shootAt(row, col);
        updateButtonColor(button, hit, true);

        if (aiBoard.allShipsSunk()) {
            showEndGameDialog("You won! Play again?");
            statusLabel.setForeground(new Color(0xFFD700)); // gold
            return;
        }

        statusLabel.setText("AI's turn");
        statusLabel.setForeground(new Color(0x808080)); // gray (AI turn)

        Timer timer = new Timer(1000, (ActionEvent e) -> {
            aiShoots();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void aiShoots() {
        int[] shot = ai.getNextShot();
        boolean hit = playerBoard.shootAt(shot[0], shot[1]);
        updatePlayerBoardButton(shot[0], shot[1], hit);

        if (playerBoard.allShipsSunk()) {
            showEndGameDialog("You lost! Play again?");
            statusLabel.setForeground(new Color(0x808080)); // gray
            return;
        }

        statusLabel.setText("Your turn");
        statusLabel.setForeground(new Color(0x00008B)); // dark blue
    }

    private void updateButtonColor(JButton btn, boolean hit, boolean playerShot) {
        if (playerShot) {
            if (hit) {
                btn.setBackground(new Color(0x00FF00)); // player hit: green
                statusLabel.setText("Hit!");
                statusLabel.setForeground(new Color(0x00AA00));
            } else {
                btn.setBackground(new Color(0xFFA500)); // player miss: orange
                statusLabel.setText("Miss!");
                statusLabel.setForeground(new Color(0xFF6600));
            }
        }
    }

    private void updatePlayerBoardButton(int row, int col, boolean hit) {
        int index = (row + 1) * (SIZE + 1) + (col + 1);
        Component comp = playerPanel.getComponent(index);
        if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            if (hit) {
                btn.setBackground(new Color(0xFF0000)); // AI hit: red
            } else {
                btn.setBackground(new Color(0x800080)); // AI miss: purple
            }
        }
    }

    private void showEndGameDialog(String message) {
        int response = JOptionPane.showConfirmDialog(this, message, "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            dispose();
        }
    }

    private void resetGame() {
        playerBoard = new Board();
        aiBoard = new Board();
        ai = new AIPlayer();

        currentShipIndex = 0;
        placingShips = true;
        horizontalPlacement = true;
        orientationToggleBtn.setEnabled(true);
        orientationToggleBtn.setText("Orientation: Horizontal");

        getContentPane().removeAll();
        setupUI();
        revalidate();
        repaint();

        statusLabel.setText("Place your " + shipsToPlace[currentShipIndex].getName() +
                " (size " + shipsToPlace[currentShipIndex].getSize() + ") - Click to place");
        statusLabel.setForeground(new Color(0x00008B)); // dark blue
    }
      public static void main(String[] args) {
        Board playerBoard = new Board();
        Board aiBoard = new Board();
        AIPlayer ai = new AIPlayer();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new BattleshipGameGUI(playerBoard, aiBoard, ai);
        });
    }
}
