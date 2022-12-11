package aoc.day11;

import aoc.Aoc;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Day11 {

    public static void main(final String[] args) {
        final String input = Aoc.readInput(11);
        final String[] monkeySections = input.split("\n\n");

        final List<Monkey> monkeyList = new ArrayList<>();
        final State state = new State(monkeyList);
        parseMonkeys(monkeySections, monkeyList);

        long num = 1;
        while (true) {
            final long fn = num;
            if (monkeyList.stream().allMatch(monkey -> fn % monkey.getTestValue() == 0)) {
                break;
            }
            num++;
        }
        state.setDivisor(num);

        state.setRidiculous(false);
        while (state.getRound() < 20) {
            state.turn();
        }
        System.out.println("Monkey business after 20 rounds: " + calculateMonkeyBusiness(monkeyList));

        state.reset();
        monkeyList.clear();
        parseMonkeys(monkeySections, monkeyList);
        state.setRidiculous(true);
        while (state.getRound() < 10000) {
            state.turn();
        }
        System.out.println("Monkey business after 10,000 rounds (mode: ridiculous): " + calculateMonkeyBusiness(monkeyList));
    }

    private static long calculateMonkeyBusiness(final List<Monkey> monkeyList) {
        return monkeyList.stream()
                .sorted(Comparator.comparingInt(v -> ((Monkey) v).getInspectedItems()).reversed())
                .limit(2)
                .mapToLong(Monkey::getInspectedItems)
                .reduce((left, right) -> left * right)
                .orElse(-1);
    }

    private static void parseMonkeys(final String[] monkeySections, final List<Monkey> monkeyList) {
        int id = 0;
        for (final String monkeySection : monkeySections) {
            final String[] lines = monkeySection.split("\n");
            final List<BigInteger> inventory = Arrays.stream(lines[1].split(":", 2)[1].trim().split(", ")).map(Long::parseLong).map(BigInteger::valueOf).toList();
            final Consumer<State> operation = parseOperation(lines);
            final Predicate<State> test = parseTest(lines);
            final long testValue = parseTestVal(lines);
            final Consumer<State> testSuccess = parseTestBranch(id, lines[4]);
            final Consumer<State> testFail = parseTestBranch(id, lines[5]);
            monkeyList.add(new Monkey(inventory, operation, test, testValue, testSuccess, testFail));
            id++;
        }
    }

    private static Consumer<State> parseOperation(final String[] lines) {
        final String opStr = lines[2].split("=", 2)[1].trim().substring(4);
        final char operator = opStr.charAt(0);
        final String arg = opStr.substring(2);
        return state -> {
            final BigInteger argInt;
            if (arg.equals("old")) {
                argInt = state.getWorryLevel();
            } else {
                argInt = BigInteger.valueOf(Integer.parseInt(arg));
            }

            final BigInteger newWorryLevel = switch (operator) {
                case '+' -> state.getWorryLevel().add(argInt);
                case '-' -> state.getWorryLevel().subtract(argInt);
                case '*' -> state.getWorryLevel().multiply(argInt);
                case '/' -> state.getWorryLevel().divide(argInt);
                case '%' -> state.getWorryLevel().mod(argInt);
                default -> throw new IllegalArgumentException("Invalid operator " + operator);
            };
            state.setWorryLevel(newWorryLevel);
        };
    }

    private static Predicate<State> parseTest(final String[] lines) {
        final String testCondition = lines[3].split(":", 2)[1].trim();
        if (testCondition.startsWith("divisible by")) {
            final int arg = Integer.parseInt(testCondition.substring(13));
            return state -> state.getWorryLevel().mod(BigInteger.valueOf(arg)).equals(BigInteger.ZERO);
        } else {
            throw new IllegalArgumentException("Invalid condition: " + testCondition);
        }
    }

    private static Long parseTestVal(final String[] lines) {
        final String testCondition = lines[3].split(":", 2)[1].trim();
        if (testCondition.startsWith("divisible by")) {
            return Long.parseLong(testCondition.substring(13));
        } else {
            throw new IllegalArgumentException("Invalid condition: " + testCondition);
        }
    }

    private static Consumer<State> parseTestBranch(final int id, final String line) {
        final String operation = line.split(":", 2)[1].trim();
        if (operation.startsWith("throw to monkey")) {
            final int monkey = Integer.parseInt(operation.substring(16));
            return state -> {
                state.getMonkeyByIndex(monkey).giveItem(state, state.getMonkeyByIndex(id).currentItem());
            };
        } else {
            throw new IllegalArgumentException("Invalid test branch: " + operation);
        }
    }

}
