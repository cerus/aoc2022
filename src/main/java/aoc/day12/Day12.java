package aoc.day12;

import aoc.Aoc;
import java.util.ArrayList;
import java.util.List;

public class Day12 {

    public static void main(final String[] args) {
        //
        // REMEMBER:
        // You don't go to the contest with the code you wish you had, you go to the contest with the code you just wrote.
        //

        final List<String> lines = new ArrayList<>();
        Vec2 start = null;
        Vec2 end = null;

        int y = 0;
        for (final String line : Aoc.readInput(12).split("\n")) {
            lines.add(line);
            if (line.contains("S")) {
                start = new Vec2(line.indexOf('S'), y);
            }
            if (line.contains("E")) {
                end = new Vec2(line.indexOf('E'), y);
            }
            y++;
        }

        final Grid grid = new Grid(lines.get(0).length(), lines.size());
        for (y = 0; y < lines.size(); y++) {
            final String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                int elevation = line.charAt(x);
                if (elevation == 'E') {
                    elevation = 'z';
                }
                if (elevation == 'S') {
                    elevation = 'a';
                }
                grid.set(y, x, elevation);
            }
        }

        System.out.println("From " + start + " (" + ((char) grid.get(start)) + ")");
        System.out.println("To " + end + " (" + ((char) grid.get(end)) + ")");
        System.out.println();

        final List<Vec2> pathOne = Pathfinder.findPath(grid, start, end);
        List<Vec2> pathTwo = new ArrayList<>();
        int pathTwoSteps = 0;
        int n = 0;
        int k = 0;
        for (int i = 0; i < 2; i++) {
            for (y = 0; y < grid.height(); y++) {
                for (int x = 0; x < grid.width(); x++) {
                    if (grid.get(y, x) == 'a') {
                        if (i == 0) {
                            n++;
                        } else {
                            System.out.println((++k) + " / " + n);
                            final List<Vec2> path = Pathfinder.findPath(grid, new Vec2(x, y), end);
                            final int pathSteps = calcSteps(path);
                            if (!path.isEmpty() && pathSteps > 0 && (pathTwo.isEmpty() || pathTwoSteps > pathSteps)) {
                                System.out.println(pathTwoSteps + " > " + pathSteps + " (" + path.size() + ")");
                                pathTwo = path;
                                pathTwoSteps = pathSteps;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(calcSteps(pathOne) + " / " + pathOne.size());
        System.out.println(calcSteps(pathTwo) + " / " + pathTwo.size());
    }

    private static int calcSteps(final List<Vec2> ogList) {
        Vec2 last = null;
        int steps = 0;
        int idx = 0;
        while (idx < ogList.size()) {
            final Vec2 vec2 = ogList.get(idx++);
            if (last != null) {
                steps += Math.max(vec2.y, last.y) - Math.min(vec2.y, last.y);
                steps += Math.max(vec2.x, last.x) - Math.min(vec2.x, last.x);
            }
            last = vec2;
        }
        return steps;
    }

}
