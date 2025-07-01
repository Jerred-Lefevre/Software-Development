public class Ship {
    private String name;
    private int size;
    private int startRow;
    private int startCol;
    private boolean horizontal;
    private int hits;

    public Ship(String name, int size, int startRow, int startCol, boolean horizontal) {
        this.name = name;
        this.size = size;
        this.startRow = startRow;
        this.startCol = startCol;
        this.horizontal = horizontal;
        this.hits = 0;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    // Call this method when the ship gets hit
    public void hit() {
        hits++;
    }

    // Check if ship is sunk
    public boolean isSunk() {
        return hits >= size;
    }
}
