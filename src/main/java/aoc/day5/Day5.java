package aoc.day5;

import aoc.Aoc;
import java.util.Arrays;

public class Day5 {

    public static void main(final String[] args) {
        final String[] parts = Aoc.readInput(5).split("\n\n");
        final String cratePart = parts[0];
        final String instructionsPart = parts[1];
        final String[] cratePartLines = cratePart.split("\n");
        final int stacks = (int) Arrays.stream(cratePartLines[cratePartLines.length - 1].split("\\s+"))
                .filter(s -> s.matches("\\d+")).count();

        final CargoStacks cargoStacks = new CargoStacks();
        for (int round = 0; round < 2; round++) {
            cargoStacks.init(stacks);
            for (int i = cratePartLines.length - 2; i >= 0; i--) {
                final String line = cratePartLines[i];
                for (int stack = 0; stack < stacks; stack++) {
                    final int charIndex = 1 + stack * 4;
                    if (charIndex < line.length() && Character.isAlphabetic(line.charAt(charIndex))) {
                        cargoStacks.push(stack, line.charAt(charIndex));
                    }
                }
            }

            for (final String instruction : instructionsPart.split("\n")) {
                if (!instruction.startsWith("move")) {
                    throw new IllegalStateException("Unknown instruction");
                }

                final String[] numStrs = instruction.substring(5).split("\\s[a-z]+\\s");
                final int amt = Integer.parseInt(numStrs[0]);
                final int from = Integer.parseInt(numStrs[1]);
                final int to = Integer.parseInt(numStrs[2]);
                cargoStacks.move(from - 1, to - 1, amt, round > 0);
            }

            System.out.print("Topmost crates: ");
            System.out.println(cargoStacks.getTopCrates().stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
        }
    }

}
