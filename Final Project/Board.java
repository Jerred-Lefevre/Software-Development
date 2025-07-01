import java.util.ArrayList;

public class Board {
    private static final int SIZE = 6;
    private final char[][] grid;
    private final ArrayList<Ship> ships;

    // ANSI color codes (same as before)...

   public Board() {
    grid = new char[SIZE][SIZE];
    ships = new ArrayList<>();
    initializeBoard(); // ✅ Now this works
}


    // ... initializeBoard() stays the same

    // Place a ship on the board if valid, and add it to ships list
    public boolean placeShip(Ship ship) {
        int startRow = ship.getStartRow();
        int startCol = ship.getStartCol();
        int size = ship.getSize();
        boolean horizontal = ship.isHorizontal();

        // Check boundaries
        if (horizontal) {
            if (startCol + size > SIZE) {
                return false;
            }
        } else {
            if (startRow + size > SIZE) {
                return false;
            }
        }
        

        // Check overlap
        for (int i = 0; i < size; i++) {
            int row = startRow + (horizontal ? 0 : i);
            int col = startCol + (horizontal ? i : 0);
            if (grid[row][col] != '~') {
                return false;
            }
        }

        // Place ship on grid
        for (int i = 0; i < size; i++) {
            int row = startRow + (horizontal ? 0 : i);
            int col = startCol + (horizontal ? i : 0);
            grid[row][col] = 'S';
        }

        ships.add(ship);
        return true;
    }

    // Add this method inside your Board class
public char getCell(int row, int col) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
        throw new IllegalArgumentException("Coordinates out of bounds");
    }
    return grid[row][col];
}

    // Shoot at the board, mark hit or miss, return true if hit
    public boolean shootAt(int row, int col) {
        char cell = grid[row][col];

        if (cell == 'X' || cell == 'O') {
            System.out.println("You already shot there!");
            return false;
        }

        if (cell == 'S') {
            grid[row][col] = 'X';  // Mark hit
            System.out.println("Hit!");

            // Find which ship was hit and register the hit
            for (Ship ship : ships) {
                if (isCoordinateOnShip(row, col, ship)) {
                    ship.hit();
                    if (ship.isSunk()) {
                        System.out.println("You sank the " + ship.getName() + "!");
                    }
                    break;
                }
            }
            return true;
        } else {
            grid[row][col] = 'O';  // Mark miss
            System.out.println("Miss.");
            return false;
        }
    }

    // Check if all ships have been sunk
    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // Helper method to check if coordinate belongs to a ship
    private boolean isCoordinateOnShip(int row, int col, Ship ship) {
        int startRow = ship.getStartRow();
        int startCol = ship.getStartCol();
        int size = ship.getSize();
        boolean horizontal = ship.isHorizontal();

        for (int i = 0; i < size; i++) {
            int r = startRow + (horizontal ? 0 : i);
            int c = startCol + (horizontal ? i : 0);
            if (r == row && c == col) {
                return true;
            }
        }
        return false;
    }

    private void initializeBoard() {
    for (int row = 0; row < SIZE; row++) {
        for (int col = 0; col < SIZE; col++) {
            grid[row][col] = '~';
        }
    }
}

public void printBoard() {
    System.out.print("  ");
    for (int col = 1; col <= SIZE; col++) {
        System.out.print(col + " ");
    }
    System.out.println();

    for (int row = 0; row < SIZE; row++) {
        System.out.print((char) ('A' + row) + " ");
        for (int col = 0; col < SIZE; col++) {
            System.out.print(grid[row][col] + " ");
        }
        System.out.println();
    }
}

public boolean canShootAt(int row, int col) {
    char cell = grid[row][col];
    return cell != 'X' && cell != 'O';
}
    // printBoard() remains unchanged
}
