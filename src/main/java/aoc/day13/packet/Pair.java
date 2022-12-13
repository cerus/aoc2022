package aoc.day13.packet;

public class Pair {

    private final Item top;
    private final Item bottom;

    public Pair(final Item top, final Item bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public boolean isInCorrectOrder() {
        return this.top.compareTo(this.bottom) <= 0;
    }

    public Item getTop() {
        return this.top;
    }

    public Item getBottom() {
        return this.bottom;
    }

}
