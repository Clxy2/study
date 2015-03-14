package cn.clxy.game.tetris.graphics;

import java.io.Serializable;

public class Rectangle implements Serializable {

    public int x;
    public int y;
    public int width;
    public int height;

    public Rectangle() {
        this(0, 0, 0, 0);
    }

    public Rectangle(Rectangle r) {
        this(r.x, r.y, r.width, r.height);
    }

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(int width, int height) {
        this(0, 0, width, height);
    }

    public Rectangle(Point p, Dimension d) {
        this(p.x, p.y, d.width, d.height);
    }

    public Rectangle(Point p) {
        this(p.x, p.y, 0, 0);
    }

    public Rectangle(Dimension d) {
        this(0, 0, d.width, d.height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

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

    public Dimension getSize() {
        return new Dimension(width, height);
    }

    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    public boolean contains(int X, int Y) {

        int w = this.width;
        int h = this.height;
        if ((w | h) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        int x = this.x;
        int y = this.y;
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        h += y;
        // overflow || intersect
        return ((w < x || w > X) && (h < y || h > Y));
    }

    public boolean contains(Rectangle r) {
        return contains(r.x, r.y, r.width, r.height);
    }

    public boolean contains(int X, int Y, int W, int H) {

        int w = this.width;
        int h = this.height;
        if ((w | h | W | H) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if any dimension is zero, tests below must return false...
        int x = this.x;
        int y = this.y;
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        W += X;
        if (W <= X) {
            // X+W overflowed or W was zero, return false if...
            // either original w or W was zero or
            // x+w did not overflow or
            // the overflowed x+w is smaller than the overflowed X+W
            if (w >= x || W > w)
                return false;
        } else {
            // X+W did not overflow and W was not zero, return false if...
            // original w was zero or
            // x+w did not overflow and x+w is smaller than X+W
            if (w >= x && W > w)
                return false;
        }
        h += y;
        H += Y;
        if (H <= Y) {
            if (h >= y || H > h)
                return false;
        } else {
            if (h >= y && H > h)
                return false;
        }
        return true;
    }

    /**
     * Determines whether or not this <code>Rectangle</code> and the specified
     * <code>Rectangle</code> intersect. Two rectangles intersect if their intersection is nonempty.
     * @param r the specified <code>Rectangle</code>
     * @return <code>true</code> if the specified <code>Rectangle</code> and this
     *         <code>Rectangle</code> intersect; <code>false</code> otherwise.
     */
    public boolean intersects(Rectangle r) {
        int tw = this.width;
        int th = this.height;
        int rw = r.width;
        int rh = r.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = this.x;
        int ty = this.y;
        int rx = r.x;
        int ry = r.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        // overflow || intersect
        return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
    }

    /**
     * Computes the intersection of this <code>Rectangle</code> with the specified
     * <code>Rectangle</code>. Returns a new <code>Rectangle</code> that represents the intersection
     * of the two rectangles. If the two rectangles do not intersect, the result will be an empty
     * rectangle.
     * @param r the specified <code>Rectangle</code>
     * @return the largest <code>Rectangle</code> contained in both the specified
     *         <code>Rectangle</code> and in this <code>Rectangle</code>; or if the rectangles do
     *         not intersect, an empty rectangle.
     */
    public Rectangle intersection(Rectangle r) {
        int tx1 = this.x;
        int ty1 = this.y;
        int rx1 = r.x;
        int ry1 = r.y;
        long tx2 = tx1;
        tx2 += this.width;
        long ty2 = ty1;
        ty2 += this.height;
        long rx2 = rx1;
        rx2 += r.width;
        long ry2 = ry1;
        ry2 += r.height;
        if (tx1 < rx1)
            tx1 = rx1;
        if (ty1 < ry1)
            ty1 = ry1;
        if (tx2 > rx2)
            tx2 = rx2;
        if (ty2 > ry2)
            ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never overflow (they will never be
        // larger than the smallest of the two source w,h)
        // they might underflow, though...
        if (tx2 < Integer.MIN_VALUE)
            tx2 = Integer.MIN_VALUE;
        if (ty2 < Integer.MIN_VALUE)
            ty2 = Integer.MIN_VALUE;
        return new Rectangle(tx1, ty1, (int) tx2, (int) ty2);
    }

    /**
     * Computes the union of this <code>Rectangle</code> with the specified <code>Rectangle</code>.
     * Returns a new <code>Rectangle</code> that represents the union of the two rectangles.
     * <p>
     * If either {@code Rectangle} has any dimension less than zero the rules for <a
     * href=#NonExistant>non-existant</a> rectangles apply. If only one has a dimension less than
     * zero, then the result will be a copy of the other {@code Rectangle}. If both have dimension
     * less than zero, then the result will have at least one dimension less than zero.
     * <p>
     * If the resulting {@code Rectangle} would have a dimension too large to be expressed as an
     * {@code int}, the result will have a dimension of {@code Integer.MAX_VALUE} along that
     * dimension.
     * @param r the specified <code>Rectangle</code>
     * @return the smallest <code>Rectangle</code> containing both the specified
     *         <code>Rectangle</code> and this <code>Rectangle</code>.
     */
    public Rectangle union(Rectangle r) {
        long tx2 = this.width;
        long ty2 = this.height;
        if ((tx2 | ty2) < 0) {
            // This rectangle has negative dimensions...
            // If r has non-negative dimensions then it is the answer.
            // If r is non-existant (has a negative dimension), then both
            // are non-existant and we can return any non-existant rectangle
            // as an answer. Thus, returning r meets that criterion.
            // Either way, r is our answer.
            return new Rectangle(r);
        }
        long rx2 = r.width;
        long ry2 = r.height;
        if ((rx2 | ry2) < 0) {
            return new Rectangle(this);
        }
        int tx1 = this.x;
        int ty1 = this.y;
        tx2 += tx1;
        ty2 += ty1;
        int rx1 = r.x;
        int ry1 = r.y;
        rx2 += rx1;
        ry2 += ry1;
        if (tx1 > rx1)
            tx1 = rx1;
        if (ty1 > ry1)
            ty1 = ry1;
        if (tx2 < rx2)
            tx2 = rx2;
        if (ty2 < ry2)
            ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never underflow since both original rectangles
        // were already proven to be non-empty
        // they might overflow, though...
        if (tx2 > Integer.MAX_VALUE)
            tx2 = Integer.MAX_VALUE;
        if (ty2 > Integer.MAX_VALUE)
            ty2 = Integer.MAX_VALUE;
        return new Rectangle(tx1, ty1, (int) tx2, (int) ty2);
    }

    public boolean isEmpty() {
        return (width <= 0) || (height <= 0);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle) obj;
            return ((x == r.x) && (y == r.y) && (width == r.width) && (height == r.height));
        }
        return super.equals(obj);
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height="
                + height + "]";
    }

    private static final long serialVersionUID = 1L;
}
