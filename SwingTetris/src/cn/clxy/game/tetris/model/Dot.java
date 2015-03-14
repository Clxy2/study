package cn.clxy.game.tetris.model;

import java.io.Serializable;

/**
 * Dot.
 * @author clxy
 */
public class Dot implements Serializable {

    private String name;

    public Dot() {
    }

    public Dot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dot [name=" + name + "]";
    }

    private static final long serialVersionUID = 1L;
}
