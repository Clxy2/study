package cn.clxy.game.uro.graphics;

import java.io.Serializable;

import cn.clxy.tools.core.utils.BeanUtil;

public class Dimension implements Serializable {

    public int width;
    public int height;

    public Dimension() {
        this(0, 0);
    }

    public Dimension(Dimension d) {
        this(d.width, d.height);
    }

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
    public boolean equals(Object obj) {
        return BeanUtil.equals(this, obj, "width", "height");
    }

    public int hashCode() {
        int sum = width + height;
        return sum * (sum + 1) / 2 + width;
    }

    public String toString() {
        return getClass().getName() + "[width=" + width + ",height=" + height + "]";
    }

    private static final long serialVersionUID = 1L;
}
