package aoc.day5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class CargoStacks {

    private Deque<Character>[] stacks;

    public void init(final int stacks) {
        this.stacks = new ArrayDeque[stacks];
        for (int i = 0; i < stacks; i++) {
            this.stacks[i] = new ArrayDeque<>();
        }
    }

    public void move(final int fromStack, final int toStack, final int amount, final boolean fancyMode) {
        if (fancyMode) {
            final List<Character> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(this.stacks[fromStack].pop());
            }
            Collections.reverse(list);
            for (final Character crate : list) {
                this.stacks[toStack].push(crate);
            }
        } else {
            for (int i = 0; i < amount; i++) {
                this.stacks[toStack].push(this.stacks[fromStack].pop());
            }
        }
    }

    public void push(final int stack, final List<Character> crates) {
        for (final Character crate : crates) {
            this.stacks[stack].push(crate);
        }
    }

    public void push(final int stack, final Character crate) {
        this.stacks[stack].push(crate);
    }

    public void put(final int stack, final List<Character> crates) {
        for (final Character crate : crates) {
            this.stacks[stack].add(crate);
        }
    }

    public List<Character> getTopCrates() {
        final List<Character> crates = new ArrayList<>();
        for (final Deque<Character> stack : this.stacks) {
            crates.add(stack.peek());
        }
        return crates;
    }

}
