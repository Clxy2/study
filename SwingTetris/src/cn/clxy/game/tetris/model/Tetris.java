package cn.clxy.game.tetris.model;

import java.io.Serializable;

import cn.clxy.game.tetris.graphics.Dimension;
import cn.clxy.game.tetris.graphics.Point;
import cn.clxy.game.tetris.graphics.Rectangle;


/**
 * Tetris.
 * @author clxy
 */
public class Tetris implements Serializable {

    protected Dot[][] data;
    protected String name;
    private int x;
    private int y;

    public Rectangle getRectangle() {
        return new Rectangle(new Point(x, y), getSize());
    }

    public Dimension getSize() {
        return new Dimension(data[0].length, data.length);
    }

    public void setName(String name) {

        this.name = name;
        setDotName();
    }

    public void setData(Dot[][] data) {

        this.data = data;
        setDotName();
    }

    public boolean hasDot(int x, int y) {

        if (ModelUtil.outBound(data, y, x)) {
            return false;
        }
        return data[y][x] != null;
    }

    private void setDotName() {

        if (data == null) {
            return;
        }

        for (Dot[] ds : data) {
            for (Dot d : ds) {
                if (d == null) {
                    continue;
                }
                d.setName(name);
            }
        }
    }

    public String toString() {

        StringBuilder sb = new StringBuilder(name);
        sb.append(", ").append(new Point(x, y));
        sb.append("\r\n");
        sb.append(ModelUtil.printData(data));
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public Dot[][] getData() {
        return data;
    }

    public Point getLocation() {
        return new Point(x, y);
    }

    public void setLocation(Point location) {
        x = location.x;
        y = location.y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private static final long serialVersionUID = 1L;
}
