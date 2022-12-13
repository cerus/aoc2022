package aoc.day4;

import aoc.Aoc;
import java.util.Arrays;
import java.util.List;

public class Day4 {

    public static void main(final String[] args) {
        final List<Pair> pairs = Arrays.stream(Aoc.readInput(4).split("\n"))
                .map(s -> {
                    final String[] pair = s.split(",");
                    return new Pair(ofString(pair[0]), ofString(pair[1]));
                })
                .toList();
        final long fullyContainedPairs = pairs.stream()
                .filter(Pair::fullyContains)
                .count();
        final long overlappingPairs = pairs.stream()
                .filter(Pair::partiallyContains)
                .count();
        System.out.println("There are " + fullyContainedPairs + " pairs with fully overlapping assignments.");
        System.out.println("There are " + overlappingPairs + " pairs with partially overlapping assignments.");
    }

    private static Assignment ofString(final String s) {
        final String[] split = s.split("-");
        final int a = Integer.parseInt(split[0]);
        final int b = Integer.parseInt(split[1]);
        return new Assignment(Math.max(a, b), Math.min(a, b));
    }

}
