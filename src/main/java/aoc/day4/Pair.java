package aoc.day4;

public class Pair {

    private final Assignment top;
    private final Assignment bottom;

    public Pair(final Assignment top, final Assignment bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public boolean fullyContains() {
        return this.top.contains(this.bottom) || this.bottom.contains(this.top);
    }

    public boolean partiallyContains() {
        return this.top.overlaps(this.bottom) || this.bottom.overlaps(this.top);
    }

    public Assignment getTop() {
        return this.top;
    }

    public Assignment getBottom() {
        return this.bottom;
    }

}
