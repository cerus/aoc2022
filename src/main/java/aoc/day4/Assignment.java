package aoc.day4;

public record Assignment(int high, int low) {

    public boolean contains(final Assignment other) {
        return other.low >= this.low && other.high <= this.high;
    }

    public boolean overlaps(final Assignment other) {
        return (other.low <= this.low && other.high >= this.low) || (other.low <= this.high && other.low >= this.high);
    }

}
