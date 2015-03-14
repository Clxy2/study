package cn.clxy.game.uro.model;

import java.io.Serializable;

import cn.clxy.game.uro.Constant;
import cn.clxy.game.uro.graphics.Cycle;
import cn.clxy.game.uro.graphics.Point;

public class Piece implements Serializable {

    private Cycle shape = new Cycle();

    /**
     * x轴速度。
     */
    public double vx = 0;

    /**
     * y轴速度。
     */
    public double vy = 0;

    public Piece(int r, double v, double angle) {
        this(new Cycle(r), v, angle);
    }

    public Piece(Cycle c, double v, double angle) {
        this(c.getX(), c.getY(), c.r, v * Math.cos(angle), v * Math.sin(angle));
    }

    public Piece(int x, int y, int r, double vx, double vy) {
        this.shape = new Cycle(x, y, r);
        this.vx = vx;
        this.vy = vy;
    }

    public Piece tryMove() {

        Cycle c = new Cycle(getNextPoint(), shape.r);
        c.setZ(getShape().getZ());

        return new Piece(c, vx, vy);
    }

    public void move() {
        setLocation(getNextPoint());
    }

    public double getAngle() {
        return Math.toDegrees(Math.atan2(-vy, vx));
    }

    public double getV() {
        return Math.sqrt(vx * vx + vy * vy);
    }

    public int getMass() {
        return (int) Math.pow(shape.r, 3);
    }

    protected Point getNextPoint() {

        int nx = shape.getX() + getStep(vx);
        int ny = shape.getY() + getStep(vy);
        return new Point(nx, ny);
    }

    private int getStep(double v) {

        if (v == 0)
            return 0;
        if (v < 0)
            return -Constant.STEP;

        return Constant.STEP;
    }

    public void setLocation(Point p) {
        shape.setLocation(p);
    }

    public Point getLocation() {
        return shape.getLocation();
    }

    public Cycle getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "Piece [shape=" + shape + ", vx=" + vx + ", vy=" + vy + "]";
    }

    private static final long serialVersionUID = 1L;
}
