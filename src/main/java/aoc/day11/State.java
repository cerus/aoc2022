package aoc.day11;

import java.math.BigInteger;
import java.util.List;

public class State {

    private static final int DEFAULT_DIVIDER = 3;

    private final List<Monkey> monkeys;
    private int current;
    private int round;
    private BigInteger worryLevel;
    private boolean ridiculous;
    private long divisor;

    public State(final List<Monkey> monkeys) {
        this.monkeys = monkeys;
    }

    public void turn() {
        final Monkey monkey = this.getMonkeyByIndex(this.current);
        while (monkey.hasItems()) {
            monkey.inspect(this);
        }
        this.current++;

        if (this.current >= this.monkeys.size()) {
            this.current = 0;
            this.round++;
        }
    }

    public Monkey getMonkeyByIndex(final int idx) {
        return this.monkeys.get(idx);
    }

    public BigInteger incrementWorryLevel(final int by) {
        this.worryLevel = this.worryLevel.add(BigInteger.valueOf(by));
        return this.worryLevel;
    }

    public BigInteger divideWorryLevel() {
        return this.divideWorryLevel(DEFAULT_DIVIDER);
    }

    public BigInteger divideWorryLevel(final int by) {
        this.worryLevel = this.worryLevel.divide(BigInteger.valueOf(by));
        return this.worryLevel;
    }

    public BigInteger getWorryLevel() {
        return this.worryLevel;
    }

    public void setWorryLevel(final BigInteger worryLevel) {
        this.worryLevel = worryLevel;
    }

    public int getRound() {
        return this.round;
    }

    public boolean isRidiculous() {
        return this.ridiculous;
    }

    public void setRidiculous(final boolean ridiculous) {
        this.ridiculous = ridiculous;
    }

    public void reset() {
        this.current = 0;
        this.round = 0;
        this.ridiculous = false;
        this.worryLevel = BigInteger.ZERO;
    }

    public long getDivisor() {
        return this.divisor;
    }

    public void setDivisor(final long num) {
        this.divisor = num;
    }

}
