package cn.clxy.game.tetris.model;

import org.junit.Test;


public class StackTest {

    @Test
    public void testClearRow() {

        char[][] data = new char[][] { { '□', '□', '□', '□' }, { '□', '□', '□', '□' },
                { '□', '■', '□', '□' }, { '■', '■', '■', '■' }, { '□', '■', '□', '□' },
                { '■', '■', '■', '■' }, { '□', '■', '□', '□' } };
        Stack s = new Stack(ModelUtil.translate(data));

        System.out.println(s);

        s.clearRow();

        System.out.println(s);
    }

    @Test
    public void testZipRow() {

        char[][] data = new char[][] { { '□', '□', '□', '□' }, { '□', '□', '□', '□' },
                { '□', '■', '□', '□' }, { '□', '□', '□', '□' }, { '□', '■', '□', '□' },
                { '□', '□', '□', '□' }, { '□', '■', '□', '□' } };
        Stack s = new Stack(ModelUtil.translate(data));

        System.out.println(s);

        s.zipRow();

        System.out.println(s);
    }

    @Test
    public void testAddBlock() {

        Block b = Theme.getNextBlock();
        System.out.println(b);

        Stack s = Theme.createStack();
        System.out.println(s);

        s.merge(b);
        System.out.println(s);
    }

    @Test
    public void testIsCollide() {

        Block b = Theme.getNextBlock();
        Stack s = Theme.createStack();

        s.merge(b);
        System.out.println(s);

        b.setLocation(3, 0);

        System.out.println(s.isCollide(b));

        s.merge(b);
        System.out.println(s);
    }
}
