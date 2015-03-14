package cn.clxy.game.uro.graphics;

import java.io.Serializable;

public abstract class Shape implements Serializable {

    protected int x;
    protected int y;
    protected int z;

    public Point getLocation() {
        return new Point(x, y);
    }

    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract Dimension getSize();

    public abstract boolean contains(Point p);

    public abstract boolean contains(int X, int Y);

    public abstract boolean contains(Shape shape);

    public abstract boolean intersects(Shape shape);

    public abstract boolean isEmpty();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    private static final long serialVersionUID = 1L;
}
