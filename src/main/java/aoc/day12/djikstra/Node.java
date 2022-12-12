package aoc.day12.djikstra;

import aoc.day12.Vec2;

public class Node {

    private final Vec2 pos;
    private Node parent;
    private double distance;

    public Node(final Vec2 pos, final Node parent, final int distance) {
        this.pos = pos;
        this.parent = parent;
        this.distance = distance;
    }

    public Vec2 getPos() {
        return this.pos;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(final double distance) {
        this.distance = distance;
    }

}
