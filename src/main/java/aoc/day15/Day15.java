package aoc.day15;

import aoc.Aoc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day15 {

    public static void main(final String[] args) {
        final List<Sensor> sensors = Arrays.stream(Aoc.readInput(15).split("\n"))
                .map(s -> s.substring(10).split(": closest beacon is at "))
                .map(strings -> {
                    final String[] rawSensorPos = strings[0].split(", ");
                    final String[] rawBeaconPos = strings[1].split(", ");
                    return new Sensor(
                            new Vec2(Integer.parseInt(rawSensorPos[0].substring(2)), Integer.parseInt(rawSensorPos[1].substring(2))),
                            new Vec2(Integer.parseInt(rawBeaconPos[0].substring(2)), Integer.parseInt(rawBeaconPos[1].substring(2)))
                    );
                })
                .collect(Collectors.toList());

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for (final Sensor sensor : sensors) {
            final int localMax = sensor.getPos().getX() + sensor.getBeaconDist();
            final int localMin = sensor.getPos().getX() - sensor.getBeaconDist();
            if (localMax > maxX) {
                maxX = localMax;
            }
            if (localMin < minX) {
                minX = localMin;
            }
        }

        System.out.println(positionsWhereBeaconCantBe(sensors, 2000000, minX, maxX));

        // Fuck this
        // Bruteforcing would take about 4 hours
        // I've looked up actual solutions and I don't understand how they work, so I give up

        final AtomicInteger counter = new AtomicInteger(0);
        final AtomicLong start = new AtomicLong(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            final int finalMinX = minX;
            final int finalMaxX = maxX;
            final int step = 4000000 / 10;
            final int from = i * step;
            final int to = (i + 1) * step;

            if (true) {
                System.out.println(i + ": " + from + ", " + to);
            }

            new Thread(() -> {
                final List<Vec2> matches = new ArrayList<>();
                for (int y = from; y <= to; y++) {
                    final int a = positionsWhereBeaconCantBe(sensors, y, 0, 4000000);
                    //System.out.println(Thread.currentThread().getName() + " | " + y + ": " + a);
                    if (a != 4000001) {
                        System.out.println("ADAFSEDFSDF");
                        for (int x = 0; x <= 4000000; x++) {
                            final Vec2 pos = new Vec2(x, y);
                            if (sensors.stream().noneMatch(s -> s.isInRange(pos))) {
                                System.out.println("FOUND: " + pos.x + "," + pos.y + ": " + ((pos.x * 4000000) + pos.y));
                                matches.add(pos);
                            }
                        }
                    }
                    if (counter.incrementAndGet() % 5000 == 0) {
                        System.out.println("# " + String.format("%,d", counter.get()) + " : " + String.format("%.6f", ((((double) counter.get()) / 4000000d) * 100d)));
                        final long diff = System.currentTimeMillis() - start.get();
                        System.out.println("Section took " + diff + "ms (" + (diff / 1000) + "s)");
                        start.set(System.currentTimeMillis());
                    }
                }

                for (final Vec2 match : matches) {
                    System.out.println("Match: " + match.x + "," + match.y + ": " + ((match.x * 4000000) + match.y));
                }
            }, "Runner-" + i).start();
        }
    }

    private static int positionsWhereBeaconCantBe(final Collection<Sensor> sensors, final int row, final int minX, final int maxX) {
        int count = 0;
        final List<Sensor> list = sensors.stream()
                .filter(sensor -> sensor.isInRange(new Vec2(sensor.getPos().x, row)))
                .toList();
        for (int x = minX; x <= maxX; x++) {
            final Vec2 pos = new Vec2(x, row);
            for (final Sensor sensor : list) {
                if (sensor.isInRange(pos) && !sensor.getBeaconPos().equals(pos)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public static int manhattanDist(final Vec2 a, final Vec2 b) {
        return pos(a.x - b.x) + pos(a.y - b.y);
    }

    private static int pos(final int i) {
        return i < 0 ? -1 * i : i;
    }

}
