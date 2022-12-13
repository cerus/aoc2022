package aoc.day13.packet;

import java.util.List;
import java.util.stream.Collectors;

public class ListItem implements Item {

    private final List<Item> items;

    public ListItem(final List<Item> items) {
        this.items = items;
    }

    @Override
    public int compareTo(final Item o) {
        if (o instanceof IntItem ii) {
            return this.compareTo(new ListItem(List.of(ii)));
        }
        if (o instanceof ListItem li) {
            for (int i = 0; i < Math.max(this.items.size(), li.items.size()); i++) {
                if (i >= this.items.size()) {
                    return -1;
                }
                if (i >= li.items.size()) {
                    return 1;
                }
                final int comp = this.items.get(i).compareTo(li.items.get(i));
                if (comp != 0) {
                    return comp;
                }
            }
            return 0;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String asString() {
        return "[" + this.items.stream()
                .map(Item::asString)
                .collect(Collectors.joining(", ")) + "]";
    }

    public List<Item> getItems() {
        return this.items;
    }

}
