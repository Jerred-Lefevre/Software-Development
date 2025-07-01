import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        runPvAIMode(scanner); // or runPvPMode(scanner);
        scanner.close();
    }

    public static void runPvAIMode(Scanner scanner) {
        AIPlayer ai = new AIPlayer();
        Board playerBoard = new Board();
        Board aiBoard = ai.getBoard();

        Ship[] ships = {
            new Ship("Carrier", 4, 0, 0, true),
            new Ship("Destroyer", 3, 0, 0, true),
            new Ship("Patrol Boat", 2, 0, 0, true)
        };

        System.out.println("Place your ships:");
        for (int i = 0; i < ships.length; i++) {
            ships[i] = placeShip(scanner, ships[i], playerBoard);
        }

        ai.placeShips(ships.clone());

        System.out.println("All ships placed! Let the battle begin.");

        while (true) {
            System.out.println("\n--- Your Turn ---");
            aiBoard.printBoard();

            int[] coords = getShot(scanner);
            if (coords == null) return;

            if (!aiBoard.canShootAt(coords[0], coords[1])) {
                System.out.println("You already shot there! Try again.");
                continue;
            }

            aiBoard.shootAt(coords[0], coords[1]);

            if (aiBoard.allShipsSunk()) {
                System.out.println("You win! All enemy ships sunk.");
                break;
            }

            System.out.println("\n--- AI's Turn ---");
            int[] shot = ai.getNextShot();
            System.out.printf("AI shoots at %c%d\n", (char) ('A' + shot[0]), shot[1] + 1);
            playerBoard.shootAt(shot[0], shot[1]);

            if (playerBoard.allShipsSunk()) {
                System.out.println("You lost! All your ships have been sunk.");
                break;
            }
        }
    }

    public static void runPvPMode(Scanner scanner) {
        Board board1 = new Board();
        Board board2 = new Board();

        Ship[] ships = {
            new Ship("Carrier", 4, 0, 0, true),
            new Ship("Destroyer", 3, 0, 0, true),
            new Ship("Patrol Boat", 2, 0, 0, true)
        };

        System.out.println("Player 1, place your ships:");
        Ship[] player1Ships = new Ship[ships.length];
        for (int i = 0; i < ships.length; i++) {
            player1Ships[i] = placeShip(scanner, ships[i], board1);
        }

        System.out.println("\nPlayer 2, place your ships:");
        Ship[] player2Ships = new Ship[ships.length];
        for (int i = 0; i < ships.length; i++) {
            player2Ships[i] = placeShip(scanner, ships[i], board2);
        }

        boolean player1Turn = true;

        while (true) {
            Board currentTarget = player1Turn ? board2 : board1;
            String playerName = player1Turn ? "Player 1" : "Player 2";

            System.out.println("\n--- " + playerName + "'s Turn ---");
            currentTarget.printBoard();

            int[] coords = getShot(scanner);
            if (coords == null) return;

            if (!currentTarget.canShootAt(coords[0], coords[1])) {
                System.out.println("That spot was already targeted. Try again.");
                continue;
            }

            currentTarget.shootAt(coords[0], coords[1]);

            if (currentTarget.allShipsSunk()) {
                System.out.println(playerName + " wins!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }

    private static Ship placeShip(Scanner scanner, Ship ship, Board board) {
        boolean placed = false;
        while (!placed) {
            System.out.println("Place your " + ship.getName() + " (size " + ship.getSize() + ")");
            System.out.print("Enter starting coordinate (e.g., A3): ");
            String coord = scanner.nextLine().toUpperCase().trim();

            if (coord.length() < 2 || coord.length() > 3) {
                System.out.println("Invalid coordinate format. Try again.");
                continue;
            }

            int row = coord.charAt(0) - 'A';
            int col;
            try {
                col = Integer.parseInt(coord.substring(1)) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid column number. Try again.");
                continue;
            }

            if (row < 0 || row >= 6 || col < 0 || col >= 6) {
                System.out.println("Coordinate out of bounds. Try again.");
                continue;
            }

            System.out.print("Enter orientation (H for horizontal, V for vertical): ");
            String orientInput = scanner.nextLine().trim().toUpperCase();
            boolean horizontal;

            if (orientInput.equals("H")) {
                horizontal = true;
            } else if (orientInput.equals("V")) {
                horizontal = false;
            } else {
                System.out.println("Invalid orientation. Try again.");
                continue;
            }

            Ship attempt = new Ship(ship.getName(), ship.getSize(), row, col, horizontal);
            placed = board.placeShip(attempt);

            if (!placed) {
                System.out.println("Invalid placement. Try again.");
            } else {
                board.printBoard();
                return attempt;
            }
        }
        return ship; // fallback
    }

    private static int[] getShot(Scanner scanner) {
        System.out.print("Enter coordinate to shoot (e.g., A3), or Q to quit: ");
        String input = scanner.nextLine().toUpperCase().trim();
        if (input.equals("Q")) {
            System.out.println("Game ended by player.");
            return null;
        }

        if (input.length() < 2 || input.length() > 3) {
            System.out.println("Invalid coordinate format. Try again.");
            return getShot(scanner);
        }

        int row = input.charAt(0) - 'A';
        int col;
        try {
            col = Integer.parseInt(input.substring(1)) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid column number. Try again.");
            return getShot(scanner);
        }

        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            System.out.println("Coordinate out of bounds. Try again.");
            return getShot(scanner);
        }

        return new int[]{row, col};
    }
}
