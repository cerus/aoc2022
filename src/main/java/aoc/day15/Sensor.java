package aoc.day15;

public class Sensor {

    private final Vec2 pos;
    private final Vec2 beaconPos;
    private final int beaconDist;

    public Sensor(final Vec2 pos, final Vec2 beaconPos) {
        this.pos = pos;
        this.beaconPos = beaconPos;
        this.beaconDist = Day15.manhattanDist(pos, beaconPos);
    }

    public boolean isInRange(final Vec2 pos) {
        return Day15.manhattanDist(this.pos, pos) <= this.beaconDist;
    }

    public Vec2 getPos() {
        return this.pos;
    }

    public Vec2 getBeaconPos() {
        return this.beaconPos;
    }

    public int getBeaconDist() {
        return this.beaconDist;
    }

}
