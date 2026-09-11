package com.github.lunatrius.schematica.util.vector;

/** A mutable three-dimensional vector backed by floats. */
public class Vector3f implements Cloneable {

    public float x;
    public float y;
    public float z;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f set(Vector3f vector) {
        return set(vector.x, vector.y, vector.z);
    }

    public Vector3i toVector3i() {
        return toVector3i(new Vector3i());
    }

    public Vector3i toVector3i(Vector3i destination) {
        return destination.set((int) Math.floor(this.x), (int) Math.floor(this.y), (int) Math.floor(this.z));
    }

    public Vector3d toVector3d() {
        return toVector3d(new Vector3d());
    }

    public Vector3d toVector3d(Vector3d destination) {
        return destination.set(this.x, this.y, this.z);
    }

    @Override
    public Vector3f clone() {
        return new Vector3f(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vector3f)) {
            return false;
        }
        Vector3f vector = (Vector3f) object;
        return Float.compare(this.x, vector.x) == 0 && Float.compare(this.y, vector.y) == 0
            && Float.compare(this.z, vector.z) == 0;
    }

    @Override
    public int hashCode() {
        int result = Float.floatToIntBits(this.x);
        result = 31 * result + Float.floatToIntBits(this.y);
        result = 31 * result + Float.floatToIntBits(this.z);
        return result;
    }
}
