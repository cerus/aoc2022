package aoc.day7;

import java.util.List;

public record Item(Item parent, List<Item> children, ItemType type, String name, int size) {

    public long nestedSize() {
        return switch (this.type) {
            case DIR -> {
                long n = 0;
                for (final Item child : this.children) {
                    n += child.nestedSize();
                }
                yield n;
            }
            case FILE -> this.size;
        };
    }

    public enum ItemType {
        FILE, DIR
    }

}
