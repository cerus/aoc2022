package aoc.day12;

import aoc.day12.djikstra.Node;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pathfinder {

    public static List<Vec2> findPath(final Grid grid, final Vec2 start, final Vec2 end) {
        return djikstra(start, end, grid);
    }

    private static List<Vec2> djikstra(final Vec2 from, final Vec2 to, final Grid grid) {
        final List<Node> settled = new ArrayList<>();
        final List<Node> unsettled = new ArrayList<>();
        for (int x = 0; x < grid.width(); x++) {
            for (int y = 0; y < grid.height(); y++) {
                if (x != from.x || y != from.y) {
                    unsettled.add(new Node(new Vec2(x, y), null, Integer.MAX_VALUE));
                } else {
                    unsettled.add(new Node(from, null, 0));
                }
            }
        }

        while (unsettled.size() > 0) {
            final Node node = unsettled.stream().sorted(Comparator.comparingDouble(Node::getDistance)).findAny().orElseThrow();
            unsettled.remove(node);

            unsettled.stream()
                    .filter(n -> canMove(grid, node.getPos(), n.getPos()))
                    .sorted(Comparator.comparingDouble(value -> dist(node.getPos(), value.getPos())))
                    .forEach(neighbour -> {
                        final double dist = node.getDistance() + dist(node.getPos(), neighbour.getPos());
                        if (dist < neighbour.getDistance()) {
                            neighbour.setDistance(dist);
                            neighbour.setParent(node);
                        }
                    });
            settled.add(node);
        }
        return walk(settled.stream()
                .filter(node -> node.getPos().equals(to))
                .findAny()
                .orElseThrow());
    }

    private static List<Vec2> walk(final Node node) {
        final List<Vec2> path = new ArrayList<>();
        Node current = node;
        while (current != null) {
            path.add(0, current.getPos());
            current = current.getParent();
        }
        return path;
    }

    private static Result traverse(final List<List<Vec2>> paths, final List<Vec2> currentPath, final Vec2 from, final Vec2 to, final Vec2 stop, final Grid grid) {
        if (!canMove(grid, from, to) || currentPath.contains(to)) {
            return Result.STUCK;
        }
        currentPath.add(to);
        if (to.equals(stop)) {
            return Result.DONE;
        }

        paths.remove(currentPath);
        for (int i = 0; i < 4; i++) {
            final Vec2 next = to.add(i == 1 ? 1 : i == 3 ? -1 : 0, i == 0 ? 1 : i == 2 ? -1 : 0);
            if (next.isPositive()) {
                final List<Vec2> nextPath = new ArrayList<>();
                if (traverse(paths, nextPath, to, next, stop, grid) == Result.STUCK) {
                    paths.remove(nextPath);
                }
            }
        }
        return Result.IN_PROGRESS;
    }

    private static boolean canMove(final Grid g, final Vec2 a, final Vec2 b) {
        try {
            final int heightDiff = g.get(a) - g.get(b);
            return heightDiff >= -1 && dist(a, b) <= 1 && isAdjacent(a, b);
        } catch (final ArrayIndexOutOfBoundsException ignored) {
            return false;
        }
    }

    private static boolean isAdjacent(final Vec2 a, final Vec2 b) {
        return a.equals(b) || (a.x == b.x && pos(a.y - b.y) <= 1) || (a.y == b.y && pos(a.x - b.x) <= 1);
    }

    private static int pos(final int i) {
        return i >= 0 ? i : -i;
    }

    private static double dist(final Vec2 a, final Vec2 b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private enum Result {
        DONE, STUCK, IN_PROGRESS
    }

}
