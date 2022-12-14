package aoc.day14;

public class Grid {

    public static final int AIR = 0;
    public static final int ROCK = 1;
    public static final int SAND = 2;
    public static final int SPAWNER = 3;
    private static final char[] STATE_CHARS = new char[] {'.', '#', 'o', '+'};

    private final int[][] grid;
    private final int width;
    private final int height;

    public Grid(final int width, final int height) {
        this.grid = new int[height][width];
        this.width = width;
        this.height = height;
    }

    public void print() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(STATE_CHARS[this.get(x, y)]);
            }
            System.out.println();
        }
    }

    public void set(final int x, final int y, final int state) {
        this.grid[y][x] = state;
    }

    public int get(final int x, final int y) {
        return this.grid[y][x];
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

}
