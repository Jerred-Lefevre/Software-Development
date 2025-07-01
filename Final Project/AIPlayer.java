import java.util.ArrayList;
import java.util.Random;

public class AIPlayer {
    private final Board board;
    private final ArrayList<int[]> previousShots;
    private final Random random;
    

    public AIPlayer() {
        this.board = new Board();
        this.previousShots = new ArrayList<>();
        this.random = new Random();
    }

    public Board getBoard() {
        return board;
    }
    

    public void placeShips(Ship[] shipsToPlace) {
        for (int i = 0; i < shipsToPlace.length; i++) {
            Ship ship = shipsToPlace[i];
            boolean placed = false;

            while (!placed) {
                int row = random.nextInt(6);
                int col = random.nextInt(6);
                boolean horizontal = random.nextBoolean();

                Ship newShip = new Ship(ship.getName(), ship.getSize(), row, col, horizontal);
                placed = board.placeShip(newShip);

                if (placed) {
                    shipsToPlace[i] = newShip;
                }
            }
        }
    }

    public int[] getNextShot() {
        int row, col;
        do {
            row = random.nextInt(6);
            col = random.nextInt(6);
        } while (hasAlreadyShot(row, col));

        previousShots.add(new int[]{row, col});
        return new int[]{row, col};
    }

    private boolean hasAlreadyShot(int row, int col) {
        for (int[] shot : previousShots) {
            if (shot[0] == row && shot[1] == col) {
                return true;
            }
        }
        return false;
    }
}
