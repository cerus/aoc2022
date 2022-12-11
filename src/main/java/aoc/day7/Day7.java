package aoc.day7;

import aoc.Aoc;
import java.util.ArrayList;
import java.util.List;

public class Day7 {

    public static void main(final String[] args) {
        final String[] lines = Aoc.readInput(7).split("\n");
        final Item root = new Item(null, new ArrayList<>(), Item.ItemType.DIR, "/", 0);
        Item current = root;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("$ ls")) {
                final List<String> output = new ArrayList<>();
                while (++i < lines.length && !lines[i].startsWith("$")) {
                    output.add(lines[i]);
                }
                i--;

                for (final String s : output) {
                    final String[] lsEntry = s.split(" ", 2);
                    if (lsEntry[0].equals("dir")) {
                        current.children().add(new Item(current, new ArrayList<>(), Item.ItemType.DIR, lsEntry[1], 0));
                    } else {
                        final int size = Integer.parseInt(lsEntry[0]);
                        current.children().add(new Item(current, new ArrayList<>(), Item.ItemType.FILE, lsEntry[1], size));
                    }
                }
            } else if (lines[i].startsWith("$ cd")) {
                final String arg = lines[i].substring(5);
                if (arg.equals("/")) {
                    current = root;
                } else if (arg.equals("..")) {
                    current = current.parent();
                } else {
                    current = current.children().stream()
                            .filter(item -> item.type() == Item.ItemType.DIR)
                            .filter(item -> item.name().equals(arg))
                            .findAny().orElse(null);
                }
            }
        }

        printTree(root, 0);
        System.out.println();

        final List<Item> dirs = new ArrayList<>();
        findSmallDirs(root, dirs, 100_000);
        System.out.println("Small dir size sum: " + dirs.stream().mapToLong(Item::nestedSize).sum());

        dirs.clear();
        final long currentAvailable = 70000000 - root.nestedSize();
        final long toDelete = 30000000 - currentAvailable;
        findBigDirs(root, dirs, toDelete);
        System.out.println("Size of best dir to delete: " + dirs.stream().mapToLong(Item::nestedSize).min().orElse(-1));
    }

    private static void findSmallDirs(final Item item, final List<Item> smallDirs, final int limit) {
        if (item.type() == Item.ItemType.DIR && item.nestedSize() <= limit) {
            smallDirs.add(item);
        }
        for (final Item child : item.children()) {
            findSmallDirs(child, smallDirs, limit);
        }
    }

    private static void findBigDirs(final Item item, final List<Item> bigDirs, final long limit) {
        if (item.type() == Item.ItemType.DIR && item.nestedSize() >= limit) {
            bigDirs.add(item);
        }
        for (final Item child : item.children()) {
            findBigDirs(child, bigDirs, limit);
        }
    }

    private static void printTree(final Item item, final int space) {
        System.out.println(" ".repeat(space) + "- " + item.name() + " (" + (item.type() == Item.ItemType.DIR ? "dir" : "file, " + item.size()) + ")");
        for (final Item child : item.children()) {
            printTree(child, space + 3);
        }
    }

}
