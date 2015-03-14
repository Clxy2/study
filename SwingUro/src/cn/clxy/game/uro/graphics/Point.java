package cn.clxy.game.uro.graphics;

import java.io.Serializable;

import cn.clxy.tools.core.utils.BeanUtil;

public class Point implements Serializable {

    public int x;
    public int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point src) {
        this.x = src.x;
        this.y = src.y;
    }

    public int calcDistance(Point p2) {
        return (int) Math.sqrt(Math.pow(Math.abs(x - p2.x), 2) + Math.pow(Math.abs(y - p2.y), 2));
    }

    /**
     * Offset the point's coordinates by dx, dy
     */
    public final void offset(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Returns true if the point's coordinates equal (x,y)
     */
    public final boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        return BeanUtil.equals(this, o, "x", "y");
    }

    @Override
    public int hashCode() {
        return x * 32713 + y;
    }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }

    private static final long serialVersionUID = 1L;
}
