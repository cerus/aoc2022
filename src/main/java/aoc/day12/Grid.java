package aoc.day12;

public class Grid {

    private final int[][] grid;
    private final int width;
    private final int height;

    public Grid(final int width, final int height) {
        this.grid = new int[height][width];
        this.width = width;
        this.height = height;
    }

    public void loadRow(final int i, final int[] row) {
        if (i < 0 || i >= this.grid.length || this.grid[i].length != row.length) {
            throw new IllegalArgumentException();
        }
        this.grid[i] = row;
    }

    public int get(final Vec2 pos) {
        return this.get(pos.y, pos.x);
    }

    public int get(final int row, final int col) {
        return this.grid[row][col];
    }

    public void set(final int row, final int col, final int i) {
        this.grid[row][col] = i;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

}
