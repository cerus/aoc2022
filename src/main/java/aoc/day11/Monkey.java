package aoc.day11;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Monkey {

    private final Deque<BigInteger> inventory;
    private final Consumer<State> operation;
    private final Predicate<State> test;
    private final long testValue;
    private final Consumer<State> testSuccessOperation;
    private final Consumer<State> testFailureOperation;
    private int inspectedItems;

    public Monkey(final Collection<BigInteger> inventory,
                  final Consumer<State> operation,
                  final Predicate<State> test,
                  final long testValue,
                  final Consumer<State> testSuccessOperation,
                  final Consumer<State> testFailureOperation) {
        this.inventory = new ArrayDeque<>(inventory);
        this.operation = operation;
        this.test = test;
        this.testValue = testValue;
        this.testSuccessOperation = testSuccessOperation;
        this.testFailureOperation = testFailureOperation;
    }

    public void inspect(final State state) {
        this.inspectedItems++;
        state.setWorryLevel(this.currentItem());
        this.operation.accept(state);
        if (!state.isRidiculous()) {
            state.divideWorryLevel();
        }
        this.inventory.pop();
        this.inventory.push(state.getWorryLevel());
        this.test(state);
        this.nextItem();
    }

    public void test(final State state) {
        if (this.test.test(state)) {
            this.testSuccessOperation.accept(state);
        } else {
            this.testFailureOperation.accept(state);
        }
    }

    public void giveItem(final State state, final BigInteger item) {
        this.inventory.addLast(item.mod(BigInteger.valueOf(state.getDivisor())).equals(BigInteger.ZERO)
                ? BigInteger.valueOf(state.getDivisor())
                : item.mod(BigInteger.valueOf(state.getDivisor())));
    }

    public BigInteger currentItem() {
        return this.inventory.peek();
    }

    public void nextItem() {
        this.inventory.pop();
    }

    public boolean hasItems() {
        return !this.inventory.isEmpty();
    }

    public Collection<BigInteger> getInventory() {
        return new ArrayList<>(this.inventory);
    }

    public int getInspectedItems() {
        return this.inspectedItems;
    }

    public long getTestValue() {
        return this.testValue;
    }

}
