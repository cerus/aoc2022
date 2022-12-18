package aoc.day17;

public class Rock {

    private final boolean[][] shape;
    private final int width;
    private final int height;

    public Rock(final int width, final int height) {
        this.shape = new boolean[height][width];
        this.width = width;
        this.height = height;
    }

    public Rock shape(final boolean[][] shape) {
        for (int i = 0; i < this.height; i++) {
            System.arraycopy(shape[i], 0, this.shape[i], 0, this.width);
        }
        return this;
    }

    public Rock shape(final String shape) {
        int y = 0;
        for (final String line : shape.split("\n")) {
            for (int x = 0; x < line.length(); x++) {
                this.shape[y][x] = line.charAt(x) == '#';
            }
            y++;
        }
        return this;
    }

    public boolean get(final int x, final int y) {
        return this.shape[y][x];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

}
