package com.github.lunatrius.schematica.util.vector;

/** A mutable three-dimensional vector backed by integers. */
public class Vector3i implements Cloneable {

    public int x;
    public int y;
    public int z;

    public Vector3i() {
        this(0, 0, 0);
    }

    public Vector3i(int x, int y, int z) {
        set(x, y, z);
    }

    public Vector3i set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3i set(Vector3i vector) {
        return set(vector.x, vector.y, vector.z);
    }

    public Vector3d toVector3d() {
        return toVector3d(new Vector3d());
    }

    public Vector3d toVector3d(Vector3d destination) {
        return destination.set(this.x, this.y, this.z);
    }

    public Vector3f toVector3f() {
        return toVector3f(new Vector3f());
    }

    public Vector3f toVector3f(Vector3f destination) {
        return destination.set(this.x, this.y, this.z);
    }

    @Override
    public Vector3i clone() {
        return new Vector3i(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vector3i)) {
            return false;
        }
        Vector3i vector = (Vector3i) object;
        return this.x == vector.x && this.y == vector.y && this.z == vector.z;
    }

    @Override
    public int hashCode() {
        int result = this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }
}
