package aoc.day18;

public class Grid3D {

    private final int width;
    private final int height;
    private final int depth;
    private final boolean[][][] grid;

    public Grid3D(final int width, final int height, final int depth) {
        this.grid = new boolean[height][width][depth];
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public void set(final int x, final int y, final int z, final boolean v) {
        this.grid[y][x][z] = v;
    }

    public boolean get(final int x, final int y, final int z) {
        return this.grid[y][x][z];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDepth() {
        return this.depth;
    }

}
