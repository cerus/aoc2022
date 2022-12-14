package aoc.day14;

import aoc.Aoc;
import java.util.Arrays;
import java.util.List;

public class Day14 {

    public static void main(final String[] args) {
        final List<Vec2[]> paths = Arrays.stream(Aoc.readInput(14).split("\n"))
                .map(s -> Arrays.stream(s.split(" -> "))
                        .map(s1 -> {
                            final String[] split = s1.split(",");
                            return new Vec2(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        })
                        .toArray(Vec2[]::new))
                .toList();
        int minX = Integer.MAX_VALUE;
        int maxY = 0;
        int width = 0;
        int height = 0;
        for (final Vec2[] path : paths) {
            for (final Vec2 point : path) {
                if (point.x > width) {
                    width = point.x;
                }
                if (point.x < minX) {
                    minX = point.x;
                }
                if (point.y > height) {
                    height = point.y;
                }
                if (point.y > maxY) {
                    maxY = point.y;
                }
            }
        }
        width -= minX;
        width += 1;
        height += 3;

        Grid grid = createGrid(width, height, minX, paths);
        runSimulation(grid, minX);
        grid.print();

        int sand = 0;
        for (int x = 0; x < grid.width(); x++) {
            for (int y = 0; y < grid.height(); y++) {
                if (grid.get(x, y) == Grid.SAND) {
                    sand++;
                    grid.set(x, y, Grid.AIR);
                }
            }
        }
        System.out.println("There are " + sand + " sand  tiles.");

        grid = createGrid((width + minX) * 2, height, minX = 0, paths);
        for (int x = 0; x < grid.width(); x++) {
            grid.set(x, maxY + 2, Grid.ROCK);
        }
        runSimulation(grid, minX);
        grid.print();

        sand = 0;
        for (int x = 0; x < grid.width(); x++) {
            for (int y = 0; y < grid.height(); y++) {
                if (grid.get(x, y) == Grid.SAND) {
                    sand++;
                    grid.set(x, y, Grid.AIR);
                }
            }
        }
        System.out.println("There are " + sand + " sand  tiles.");
    }

    private static Grid createGrid(final int width, final int height, final int minX, final List<Vec2[]> paths) {
        final Grid grid = new Grid(width, height);
        grid.set(500 - minX, 0, Grid.SPAWNER);
        for (final Vec2[] path : paths) {
            Vec2 last = null;
            for (final Vec2 point : path) {
                if (last == null) {
                    last = point;
                    continue;
                }

                final Vec2 p1 = last.sub(minX, 0);
                final Vec2 p2 = point.sub(minX, 0);
                drawLine(grid, Vec2.min(p1, p2), Vec2.max(p1, p2), Grid.ROCK);
                last = point;
            }
        }
        return grid;
    }

    private static void runSimulation(final Grid grid, final int minX) {
        main:
        while (grid.get(500 - minX, 0) == Grid.SPAWNER) {
            int x = 500 - minX;
            int y = 0;
            while (true) {
                try {
                    if (grid.get(x, y + 1) == Grid.AIR || grid.get(x, y + 1) == Grid.SPAWNER) {
                        y++;
                    } else if (grid.get(x - 1, y + 1) == Grid.AIR || grid.get(x - 1, y + 1) == Grid.SPAWNER) {
                        x--;
                        y++;
                    } else if (grid.get(x + 1, y + 1) == Grid.AIR || grid.get(x + 1, y + 1) == Grid.SPAWNER) {
                        x++;
                        y++;
                    } else {
                        break;
                    }
                } catch (final ArrayIndexOutOfBoundsException ignored) {
                    System.out.println("PANIC AT " + x + "," + y);
                    break main;
                }
            }
            grid.set(x, y, Grid.SAND);
        }
    }

    private static void drawLine(final Grid grid, final Vec2 p1, final Vec2 p2, final int state) {
        if (p1.equals(p2)) {
            return;
        }
        if (p1.x == p2.x) {
            // Y changes
            drawLineVertical(grid, p1, p2, state);
            return;
        }
        if (p1.y == p2.y) {
            // X changes
            drawLineHorizontal(grid, p1, p2, state);
            return;
        }

        final int dx = p2.x - p1.x;
        final int dy = p2.y - p1.y;
        int D = 2 * dy - dx;
        int y = p1.y;
        for (int x = p1.x; x <= p2.x; x++) {
            grid.set(x, y, state);
            if (D > 0) {
                y += 1;
                D -= 2 * dx;
            }
            D += 2 * dy;
        }
    }

    private static void drawLineVertical(final Grid grid, final Vec2 p1, final Vec2 p2, final int state) {
        for (int y = p1.y; y <= p2.y; y++) {
            grid.set(p1.x, y, state);
        }
    }

    private static void drawLineHorizontal(final Grid grid, final Vec2 p1, final Vec2 p2, final int state) {
        for (int x = p1.x; x <= p2.x; x++) {
            grid.set(x, p1.y, state);
        }
    }

}
