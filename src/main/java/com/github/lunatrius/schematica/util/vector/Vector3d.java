package com.github.lunatrius.schematica.util.vector;

/** A mutable three-dimensional vector backed by doubles. */
public class Vector3d implements Cloneable {

    public double x;
    public double y;
    public double z;

    public Vector3d() {
        this(0.0, 0.0, 0.0);
    }

    public Vector3d(double x, double y, double z) {
        set(x, y, z);
    }

    public Vector3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3d set(Vector3d vector) {
        return set(vector.x, vector.y, vector.z);
    }

    public Vector3d add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3d add(Vector3d vector) {
        return add(vector.x, vector.y, vector.z);
    }

    public Vector3d sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3d sub(Vector3d vector) {
        return sub(vector.x, vector.y, vector.z);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double lengthSquaredTo(Vector3d vector) {
        double deltaX = this.x - vector.x;
        double deltaY = this.y - vector.y;
        double deltaZ = this.z - vector.z;
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    public Vector3i toVector3i() {
        return toVector3i(new Vector3i());
    }

    public Vector3i toVector3i(Vector3i destination) {
        return destination.set((int) Math.floor(this.x), (int) Math.floor(this.y), (int) Math.floor(this.z));
    }

    public Vector3f toVector3f() {
        return toVector3f(new Vector3f());
    }

    public Vector3f toVector3f(Vector3f destination) {
        return destination.set((float) Math.floor(this.x), (float) Math.floor(this.y), (float) Math.floor(this.z));
    }

    @Override
    public Vector3d clone() {
        return new Vector3d(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vector3d)) {
            return false;
        }
        Vector3d vector = (Vector3d) object;
        return Double.compare(this.x, vector.x) == 0 && Double.compare(this.y, vector.y) == 0
            && Double.compare(this.z, vector.z) == 0;
    }

    @Override
    public int hashCode() {
        long result = Double.doubleToLongBits(this.x);
        result = 31L * result + Double.doubleToLongBits(this.y);
        result = 31L * result + Double.doubleToLongBits(this.z);
        return (int) (result ^ result >>> 32);
    }
}
