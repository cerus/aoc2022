package aoc.day13.packet;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final String string;
    private int idx;

    public Parser(final String string) {
        this.string = string;
    }

    public static Item parse(final String str) {
        return new Parser(str).parse();
    }

    public Item parse() {
        if (this.peek() == '[') {
            this.pop();
            final List<Item> items = new ArrayList<>();
            while (this.peek() != ']') {
                items.add(this.parse());
                if (this.peek() != ',' && this.peek() != ']') {
                    throw new IllegalStateException("Expected comma or closing square bracket @ " + this.idx);
                }
                if (this.peek() == ',') {
                    this.pop();
                }
            }
            this.pop();
            return new ListItem(items);
        } else {
            final StringBuilder buffer = new StringBuilder();
            while (this.peek() != ',' && this.peek() != ']') {
                buffer.append(this.pop());
            }
            return new IntItem(Integer.parseInt(buffer.toString()));
        }
    }

    private char peek() {
        return this.string.charAt(this.idx);
    }

    private char pop() {
        return this.string.charAt(this.idx++);
    }

}
