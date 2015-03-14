package cn.clxy.game.uro.graphics;

public class Rectangle extends Shape {

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

	@Override
	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	@Override
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

	@Override
	public boolean contains(Shape shape) {

		if (!(shape instanceof Cycle)) {
			return false;// Other shape not implements.
		}

		Rectangle r = (Rectangle) shape;
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

	@Override
	public boolean intersects(Shape shape) {

		if (!(shape instanceof Cycle)) {
			return false;// Other shape not implements.
		}
		Rectangle r = (Rectangle) shape;

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
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty)
				&& (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	@Override
	public boolean isEmpty() {
		return (width <= 0) || (height <= 0);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			return ((x == r.x) && (y == r.y) && (width == r.width) && (height == r.height));
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width
				+ ",height=" + height + "]";
	}

	private static final long serialVersionUID = 1L;
}
