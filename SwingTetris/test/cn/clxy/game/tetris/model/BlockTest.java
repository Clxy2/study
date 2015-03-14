package cn.clxy.game.tetris.model;

import org.junit.Test;

public class BlockTest {

    @Test
    public void testPreRotate() {

    }

    @Test
    public void testMove() {

    }

    @Test
    public void testRotate() {

        Block b = Theme.getNextBlock();
        System.out.println(b);

        b.rotate();
        System.out.println(b);

        b.rotate();
        System.out.println(b);
    }
}
