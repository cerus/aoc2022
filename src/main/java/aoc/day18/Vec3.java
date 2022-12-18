package aoc.day18;

import java.util.Objects;

public class Vec3 {

    public int x;
    public int y;
    public int z;

    public Vec3(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3 max(final Vec3 a, final Vec3 b) {
        return new Vec3(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));
    }

    public static Vec3 min(final Vec3 a, final Vec3 b) {
        return new Vec3(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));
    }

    public Vec3 add(final int x, final int y, final int z) {
        return new Vec3(this.x + x, this.y + y, this.z + z);
    }

    public Vec3 sub(final int x, final int y, final int z) {
        return this.add(-x, -y, -z);
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

    public int getZ() {
        return this.z;
    }

    public void setZ(final int z) {
        this.z = z;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Vec3 vec3 = (Vec3) o;
        return this.getX() == vec3.getX() && this.getY() == vec3.getY() && this.getZ() == vec3.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public String toString() {
        return "Vec3{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                '}';
    }

}
