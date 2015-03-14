package cn.clxy.game.uro.graphics;

import cn.clxy.tools.core.utils.BeanUtil;

/**
 * Arc is better?
 * @author clxy
 */
public class Cycle extends Shape {

    public int r;

    public Cycle() {
        this(0, 0, 0);
    }

    public Cycle(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Cycle(int r) {
        this(0, 0, r);
    }

    public Cycle(Point p, int r) {
        this(p.x, p.y, r);
    }

    @Override
    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    @Override
    public boolean contains(int x, int y) {

        int d = getLocation().calcDistance(new Point(x, y));
        return d <= r;
    }

    @Override
    public boolean contains(Shape shape) {

        if (!(shape instanceof Cycle)) {
            return false;// Other shape not implements.
        }

        int d = getLocation().calcDistance(shape.getLocation());
        return (d + ((Cycle) shape).r) <= r;
    }

    @Override
    public boolean intersects(Shape shape) {

        if (!(shape instanceof Cycle)) {
            return false;// Other shape not implements.
        }
        int d = getLocation().calcDistance(shape.getLocation());
        return (d - ((Cycle) shape).r) <= r;
    }

    @Override
    public boolean isEmpty() {
        return r <= 0;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(r, r);
    }

    @Override
    public boolean equals(Object obj) {
        return BeanUtil.equals(this, obj, "x", "y", "r");
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ", y=" + y + ", r=" + r + "]";
    }

    private static final long serialVersionUID = 1L;
}
