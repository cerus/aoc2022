package aoc.day13;

import aoc.Aoc;
import aoc.day13.packet.Item;
import aoc.day13.packet.Pair;
import aoc.day13.packet.Parser;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(final String[] args) {
        final List<Pair> pairs = Arrays.stream(Aoc.readInput(13).split("\n\n"))
                .map(s -> {
                    final String[] split = s.split("\n");
                    return new Pair(
                            Parser.parse(split[0]),
                            Parser.parse(split[1])
                    );
                })
                .toList();

        int rightOrderIndicesSum = 0;
        for (int i = 0; i < pairs.size(); i++) {
            final Pair pair = pairs.get(i);
            if (pair.isInCorrectOrder()) {
                rightOrderIndicesSum += (i + 1);
            }
        }
        System.out.println("Sum of pair indices that are in correct order: " + rightOrderIndicesSum);

        final List<Item> packets = pairs.stream()
                .flatMap(pair -> Arrays.stream(new Item[] {
                        pair.getTop(), pair.getBottom()
                }))
                .collect(Collectors.toList());
        packets.add(Parser.parse("[[2]]"));
        packets.add(Parser.parse("[[6]]"));

        int decoderKey = 1;
        packets.sort(Comparable::compareTo);
        for (final Item packet : packets) {
            if (packet.asString().equals("[[2]]")
                    || packet.asString().equals("[[6]]")) {
                decoderKey *= (packets.indexOf(packet) + 1);
            }
        }
        System.out.println("Decoder key: " + decoderKey);
    }

}
