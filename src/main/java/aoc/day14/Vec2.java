package aoc.day14;

import java.util.Objects;

public class Vec2 {

    public int x;
    public int y;

    public Vec2(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 max(final Vec2 a, final Vec2 b) {
        return new Vec2(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }

    public static Vec2 min(final Vec2 a, final Vec2 b) {
        return new Vec2(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    public Vec2 add(final int x, final int y) {
        return new Vec2(this.x + x, this.y + y);
    }

    public Vec2 sub(final int x, final int y) {
        return this.add(-x, -y);
    }

    public boolean isPositive() {
        return this.x >= 0 && this.y >= 0;
    }

    public int getX() {
        return this.x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Vec2 vec2 = (Vec2) o;
        return this.getX() == vec2.getX() && this.getY() == vec2.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + this.x +
                ", y=" + this.y +
                '}';
    }

}
