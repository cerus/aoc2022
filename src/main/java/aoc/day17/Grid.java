package aoc.day17;

public class Grid {

    private final int width;
    private boolean[][] grid;
    private int height;

    public Grid(final int width, final int height) {
        this.grid = new boolean[height][width];
        this.width = width;
        this.height = height;
    }

    public boolean fits(final Rock rock, final int x, final int y) {
        if (rock.getWidth() + x > this.width || rock.getHeight() + y > this.height) {
            return false;
        }

        for (int xx = 0; xx < rock.getWidth(); xx++) {
            for (int yy = 0; yy < rock.getHeight(); yy++) {
                if (rock.get(xx, yy) && this.grid[yy + y][xx + x]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void place(final Rock rock, final int x, final int y) {
        if (rock.getWidth() + x > this.width) {
            throw new IllegalArgumentException(x + " + " + rock.getWidth() + " > width");
        }
        if (rock.getHeight() + y > this.height) {
            throw new IllegalArgumentException(y + " + " + rock.getHeight() + " > height");
        }

        for (int xx = 0; xx < rock.getWidth(); xx++) {
            for (int yy = 0; yy < rock.getHeight(); yy++) {
                if (rock.get(xx, yy)) {
                    this.set(xx + x, yy + y, true);
                }
            }
        }
    }

    public void set(final int x, final int y, final boolean v) {
        this.grid[y][x] = v;
    }

    public void removeRowsFromTail(final int amount) {
        final boolean[][] gridCopy = this.copy();
        this.height -= amount;
        this.grid = new boolean[this.height][this.width];
        for (int y = 0; y < this.height; y++) {
            System.arraycopy(gridCopy[y], 0, this.grid[y], 0, this.width);
        }
    }

    public void addRows(final int amount) {
        final boolean[][] gridCopy = this.copy();
        this.height += amount;
        this.grid = new boolean[this.height][this.width];
        if (amount < 0) {
            for (int y = 0; y < this.height; y++) {
                System.arraycopy(gridCopy[y + (-amount)], 0, this.grid[y], 0, this.width);
            }
        } else {
            for (int y = 0; y < this.height - amount; y++) {
                System.arraycopy(gridCopy[y], 0, this.grid[y + amount], 0, this.width);
            }
        }
    }

    public void print(final char emptyChar, final char filledChar) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(this.grid[y][x] ? filledChar : emptyChar);
            }
            System.out.println();
        }
    }

    private boolean[][] copy() {
        final boolean[][] copy = new boolean[this.height][this.width];
        for (int y = 0; y < this.height; y++) {
            System.arraycopy(this.grid[y], 0, copy[y], 0, this.width);
        }
        return copy;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

}
