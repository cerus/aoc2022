package aoc.day18.djikstra;

import aoc.day18.Grid3D;
import aoc.day18.Vec3;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pathfinder {

    public static List<Vec3> findPath(final Grid3D grid, final Vec3 start, final Vec3 end) {
        return djikstra(start, end, grid);
    }

    private static List<Vec3> djikstra(final Vec3 from, final Vec3 to, final Grid3D grid) {
        final List<Node> settled = new ArrayList<>();
        final List<Node> unsettled = new ArrayList<>();
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                for (int z = 0; z < grid.getDepth(); z++) {
                    if (x != from.x || y != from.y || z != from.z) {
                        unsettled.add(new Node(new Vec3(x, y, z), null, Integer.MAX_VALUE));
                    } else {
                        unsettled.add(new Node(from, null, 0));
                    }
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

        final List<Vec3> walk = walk(settled.stream()
                .filter(node -> node.getPos().equals(to))
                .findAny()
                .orElseThrow());
        if (walk.isEmpty() || !walk.get(0).equals(from)) {
            throw new IllegalStateException();
        }
        return walk;
    }

    private static boolean canMove(final Grid3D grid, final Vec3 from, final Vec3 to) {
        return !grid.get(to.x, to.y, to.z) && !grid.get(from.x, from.y, from.z) && isAdjacent(from, to);
    }

    private static boolean isAdjacent(final Vec3 from, final Vec3 to) {
        return to.equals(from.add(1, 0, 0)) || to.equals(from.add(-1, 0, 0))
                || to.equals(from.add(0, 1, 0)) || to.equals(from.add(0, -1, 0))
                || to.equals(from.add(0, 0, 1)) || to.equals(from.add(0, 0, -1));
    }

    private static List<Vec3> walk(final Node node) {
        final List<Vec3> path = new ArrayList<>();
        Node current = node;
        while (current != null) {
            path.add(0, current.getPos());
            current = current.getParent();
        }
        return path;
    }

    private static double dist(final Vec3 a, final Vec3 b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2));
    }

}
