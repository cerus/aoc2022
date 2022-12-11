package aoc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Aoc {

    public static String readInput(final int day) {
        if (true) {
            try {
                return Files.readString(new File("src/main/resources/input%d.txt".formatted(day)).toPath());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        final StringBuilder buffer = new StringBuilder();
        try (final InputStream resourceAsStream = Aoc.class.getClassLoader().getResourceAsStream("input%d.txt".formatted(day))) {
            final byte[] buf = new byte[512];
            int read;
            while ((read = resourceAsStream.read(buf)) != -1) {
                buffer.append(new String(buf, 0, read));
            }
        } catch (final NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
        return buffer.toString();
    }

}
