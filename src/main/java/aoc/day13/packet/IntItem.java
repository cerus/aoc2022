package aoc.day13.packet;

import java.util.List;

public class IntItem implements Item {

    private final int val;

    public IntItem(final int val) {
        this.val = val;
    }

    @Override
    public int compareTo(final Item o) {
        if (o instanceof IntItem ii) {
            return Integer.compare(this.val, ii.val);
        }
        if (o instanceof ListItem li) {
            return new ListItem(List.of(this)).compareTo(li);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String asString() {
        return String.valueOf(this.val);
    }

    public int getVal() {
        return this.val;
    }

}
