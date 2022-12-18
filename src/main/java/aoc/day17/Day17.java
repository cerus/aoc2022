package aoc.day17;

import aoc.Aoc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    public static void main(final String[] args) {
        final List<Rock> rocks = Arrays.asList(
                new Rock(4, 1).shape("####"),
                new Rock(3, 3).shape("""
                        .#.
                        ###
                        .#.
                        """),
                new Rock(3, 3).shape("""
                        ..#
                        ..#
                        ###
                        """),
                new Rock(1, 4).shape("""
                        #
                        #
                        #
                        #
                        """),
                new Rock(2, 2).shape("""
                        ##
                        ##
                        """)
        );
        final List<Boolean> jetsOriginal = Arrays.stream(Aoc.readInput(17).trim().split(""))
                .map(s -> s.charAt(0) == '>')
                .collect(Collectors.toList());
        final List<Boolean> jets = new ArrayList<>(jetsOriginal);

        int rockIdx = 0;
        long highest = 0;
        final long heightMod = 0;
        final Grid grid = new Grid(7, 1);
        for (long i = 0; i < 2022L; i++) {
            if (rockIdx >= rocks.size()) {
                rockIdx = 0;
            }
            final Rock rock = rocks.get(rockIdx++);
            final long rowsRequired = highest + 3 + rock.getHeight();
            final int yAdd = 0;
            if (grid.getHeight() != rowsRequired) {
                grid.addRows((int) (rowsRequired - grid.getHeight()));
            }

            int x = 2;
            int y = yAdd;
            while (grid.fits(rock, x, y)) {
                if (jets.isEmpty()) {
                    jets.addAll(jetsOriginal);
                }
                final boolean movement = jets.remove(0);
                final int xBefore = x;
                if (movement) {
                    // To the right
                    x = Math.min(x + 1, grid.getWidth() - rock.getWidth());
                } else {
                    // To the left
                    x = Math.max(x - 1, 0);
                }

                if (!grid.fits(rock, x, y)) {
                    x = xBefore;
                }
                y++;
                if (!grid.fits(rock, x, y)) {
                    y--;
                    break;
                }
            }

            grid.place(rock, x, y);
            highest = Math.max(highest, grid.getHeight() - y);
        }

        grid.print('+', '#');
        System.out.println("2022 height: " + (highest + heightMod));
    }

}
