package aoc.day16;

import aoc.Aoc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day16 {

    public static void main(final String[] args) {
        final Map<String, Valve> valveMap = Arrays.stream(Aoc.readInput(16).split("\n"))
                .map(s -> {
                    final String[] mainParts = s.split("; ");
                    final String identifier = mainParts[0].split("\\s+")[1];
                    final int flowRate = Integer.parseInt(mainParts[0].substring(mainParts[0].lastIndexOf(' ') + 6));
                    final List<String> connections = Arrays.stream(mainParts[1].split("valve(s)? ")[1].split(", "))
                            .collect(Collectors.toList());
                    return new Valve(identifier, flowRate, connections);
                })
                .collect(Collectors.toMap(Valve::getIdentifier, v -> v));

    }

    private static List<String> calculateOptimalPath(final Map<String, Valve> valveMap, final Valve valve, final int time) {
        final int remainingTime = time;
        final List<String> path = new ArrayList<>();
        for (final String connection : valve.getConnections()) {
            final Valve connectedValve = valveMap.get(connection);
            if (connectedValve.isOpen()) {
                // Already opened this one
            }
        }
        return null;
    }

}
