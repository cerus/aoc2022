package aoc.day18;

import aoc.Aoc;
import aoc.day18.djikstra.Pathfinder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Day18 {

    public static void main(final String[] args) {
        final List<Vec3> cubes = Arrays.stream(Aoc.readInput(18).split("\n"))
                .map(s -> {
                    final String[] split = s.split(",");
                    return new Vec3(
                            Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]),
                            Integer.parseInt(split[2])
                    );
                })
                .toList();

        final int totalNonConnectedSides = cubes.stream()
                .mapToInt(value -> countNonConnectedSides(value, cubes, null, 0, 0, 0))
                .sum();

        final int minX = cubes.stream().mapToInt(v -> v.x).min().orElse(0);
        final int maxX = cubes.stream().mapToInt(v -> v.x).max().orElse(0);
        final int minY = cubes.stream().mapToInt(v -> v.y).min().orElse(0);
        final int maxY = cubes.stream().mapToInt(v -> v.y).max().orElse(0);
        final int minZ = cubes.stream().mapToInt(v -> v.z).min().orElse(0);
        final int maxZ = cubes.stream().mapToInt(v -> v.z).max().orElse(0);
        final int width = maxX - minX + 3;
        final int height = maxY - minY + 3;
        final int depth = maxZ - minZ + 3;

        final Grid3D grid = new Grid3D(width, height, depth);
        for (final Vec3 cube : cubes) {
            grid.set(cube.x - minX, cube.y - minY, cube.z - minZ, true);
        }

        final AtomicInteger totalNonConnectedFreestandingSidesAtomic = new AtomicInteger(0);
        final AtomicInteger index = new AtomicInteger(0);
        final AtomicInteger running = new AtomicInteger(0);
        final List<Vec3> cubeList = new CopyOnWriteArrayList<>(cubes);
        for (int i = 0; i < 8; i++) {
            running.incrementAndGet();
            new Thread(() -> {
                while (index.get() < cubeList.size()) {
                    final Vec3 element = cubeList.get(index.getAndIncrement());
                    totalNonConnectedFreestandingSidesAtomic.addAndGet(countNonConnectedSides(element, cubes, grid, minX, minY, minZ));
                    System.out.println((cubeList.size() - index.get()) + " remain | " + Thread.currentThread().getName());
                }
                running.decrementAndGet();
            }, "Runner-" + i).start();
        }

        while (running.get() > 0) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Amount of non-connected sides: " + totalNonConnectedSides);
        System.out.println("Amount of non-connected freestanding sides: " + totalNonConnectedFreestandingSidesAtomic.get());
    }

    private static int countNonConnectedSides(final Vec3 cube, final List<Vec3> cubes, final Grid3D grid, final int minX, final int minY, final int minZ) {
        int count = 0;
        if (!cubes.contains(cube.add(1, 0, 0)) && (grid == null || isFreestanding(cube.add(1, 0, 0), grid, minX, minY, minZ))) {
            count++;
        }
        if (!cubes.contains(cube.add(-1, 0, 0)) && (grid == null || isFreestanding(cube.add(-1, 0, 0), grid, minX, minY, minZ))) {
            count++;
        }
        if (!cubes.contains(cube.add(0, 0, 1)) && (grid == null || isFreestanding(cube.add(0, 0, 1), grid, minX, minY, minZ))) {
            count++;
        }
        if (!cubes.contains(cube.add(0, 0, -1)) && (grid == null || isFreestanding(cube.add(0, 0, -1), grid, minX, minY, minZ))) {
            count++;
        }
        if (!cubes.contains(cube.add(0, 1, 0)) && (grid == null || isFreestanding(cube.add(0, 1, 0), grid, minX, minY, minZ))) {
            count++;
        }
        if (!cubes.contains(cube.add(0, -1, 0)) && (grid == null || isFreestanding(cube.add(0, -1, 0), grid, minX, minY, minZ))) {
            count++;
        }
        return count;
    }

    private static boolean isFreestanding(final Vec3 point, final Grid3D grid, final int minX, final int minY, final int minZ) {
        if (!(point.x - minX >= 0 && point.x - minX < grid.getWidth()
                && point.y - minY >= 0 && point.y - minY < grid.getHeight()
                && point.z - minZ >= 0 && point.z - minZ < grid.getDepth())) {
            return true;
        }

        // Using exceptions for this sort of stuff is probably not the best idea
        try {
            Pathfinder.findPath(grid, point.sub(minX, minY, minZ), new Vec3(grid.getWidth() - 1, grid.getHeight() - 1, grid.getDepth() - 1));
            return true;
        } catch (final Throwable t) {
            return false;
        }
    }

}
