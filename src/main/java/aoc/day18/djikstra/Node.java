package aoc.day18.djikstra;

import aoc.day18.Vec3;

public class Node {

    private final Vec3 pos;
    private Node parent;
    private double distance;

    public Node(final Vec3 pos, final Node parent, final int distance) {
        this.pos = pos;
        this.parent = parent;
        this.distance = distance;
    }

    public Vec3 getPos() {
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
