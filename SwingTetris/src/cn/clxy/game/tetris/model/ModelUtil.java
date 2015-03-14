package cn.clxy.game.tetris.model;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import cn.clxy.game.tetris.graphics.Dimension;
import cn.clxy.game.tetris.graphics.Point;
import cn.clxy.game.tetris.graphics.Rectangle;

/**
 * Model utils
 * @author clxy
 */
public final class ModelUtil {

    /**
     * @param stack
     * @return
     */
    public static Boolean isDanger(Stack stack) {

        return getHighest(stack) * 2 - stack.getSize().height > 0;
    }

    /**
     * @param stack
     * @return
     */
    public static int getHighest(Stack stack) {
        if (stack == null) {
            return 0;
        }

        Dot[][] stackData = stack.getData();
        for (int y = 0, h = stackData.length; y < h; y++) {

            if (!isBlankRow(stackData[y])) {
                return stack.getSize().height - y;
            }
        }

        return 0;
    }

    /**
     * @param stack
     * @return
     */
    public static int countSpots(Stack stack) {
        return countSpots(stack, stack.getRectangle());
    }

    /**
     * @param stack
     * @param block
     * @return
     */
    public static int countSpots(Stack stack, Rectangle rec) {

        int spot = 0;

        for (int x = rec.x, w = rec.x + rec.width; x < w; x++) {

            boolean hadCover = false;
            for (int y = rec.y, h = rec.y + rec.height; y < h; y++) {

                if (stack.hasDot(x, y)) {
                    hadCover = true;
                    continue;
                }

                if (hadCover) {
                    spot++;
                }
            }
        }

        return spot;
    }

    /**
     * @param stack
     * @param block
     * @return
     */
    public static int countUnions(Stack stack, Block block) {

        if (stack == null || block == null) {
            return 0;
        }

        int result = 0;
        Point loc = block.getLocation();
        Dot[][] blockData = block.getData();

        for (int y = 0, h = blockData.length; y < h; y++) {
            for (int x = 0, w = blockData[y].length; x < w; x++) {

                Dot d = blockData[y][x];
                if (d == null) {
                    continue;
                }
                result += countUnion(stack, loc.x + x, loc.y + y);
            }
        }
        return result;
    }

    private static int countUnion(Stack stack, int x, int y) {

        int result = 0;

        if (x == 0 || stack.hasDot(x - 1, y)) {
            result++;// left
        }

        if ((x + 1) == stack.getSize().width || stack.hasDot(x + 1, y)) {
            result++;// right
        }

        if (stack.hasDot(x, y - 1)) {
            result++;// above
        }

        if ((y + 1) == stack.getSize().height || stack.hasDot(x, y + 1)) {
            result++;// under
        }

        return result;
    }

    /**
     * Create shadow on y direction.
     * @param stack
     * @param block
     * @return
     */
    public static Block createShadow(Stack stack, Block block) {

        Block shadow = block.copy();

        while (true) {

            Point loc = shadow.getLocation();
            loc.offset(0, 1);

            if (stack.isCollide(shadow.preSetLocation(loc))) {
                return shadow;
            }
            shadow.setLocation(loc);
        }
    }

    /**
     * @param stack
     * @param pos1
     * @param pos2
     */
    public static void switchRow(Dot[][] stack, int pos1, int pos2) {

        Dot[] row1 = stack[pos1];
        Dot[] row2 = stack[pos2];
        stack[pos1] = row2;
        stack[pos2] = row1;
    }

    /**
     * @param t
     * @return
     */
    public static Dot[][] copyData(Tetris t) {

        Dimension size = t.getSize();
        Dot[][] result = new Dot[size.height][size.width];

        Dot[][] data = t.getData();
        for (int i = 0; i < size.height; i++) {
            System.arraycopy(data[i], 0, result[i], 0, data[i].length);
        }

        return result;
    }

    /**
     * @param stack
     * @param block
     * @return
     */
    public static Dot[][] mergeData(final Tetris stack, final Tetris block) {

        Dot[][] result = copyData(stack);
        if (block == null) {
            return result;
        }

        Point loc = block.getLocation();
        Dot[][] blockData = block.getData();

        for (int i = 0, h = blockData.length; i < h; i++) {
            for (int j = 0, w = blockData[i].length; j < w; j++) {
                Dot d = blockData[i][j];
                if (d == null || outBound(result, i + loc.y, j + loc.x)) {
                    continue;
                }
                result[i + loc.y][j + loc.x] = d;
            }
        }
        return result;
    }

    public static Dot[][] translate(char[][] cs) {

        int h = cs.length;
        int w = cs[0].length;
        Dot[][] data = new Dot[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (cs[y][x] != '■') {
                    continue;
                }
                data[y][x] = new Dot();
            }
        }
        return data;
    }

    public static boolean isFullRow(Dot[] row) {
        return isAll(row, true);
    }

    public static boolean isBlankRow(Dot[] row) {
        return isAll(row, false);
    }

    public static String printData(Object[][] data) {

        StringBuilder sb = new StringBuilder();
        for (Object[] d : data) {
            for (Object b : d) {
                sb.append(b == null ? '□' : '■');
            }
            sb.append("\r\n");
        }

        return sb.toString();
    }

    public static <T> T getRandom(Collection<T> ts) {

        if (ts == null || ts.isEmpty()) {
            return null;
        }

        T[] a = (T[]) ts.toArray();
        Random r = new Random(new Date().getTime());

        int result = r.nextInt(a.length);
        return a[result];
    }

    public static <T> boolean outBound(T[][] t, int i1, int i2) {

        if (i1 < 0 || i2 < 0) {
            return true;
        }

        if (i1 >= t.length || i2 >= t[i1].length) {
            return true;
        }

        return false;
    }

    private static boolean isAll(Dot[] row, boolean notNull) {

        for (Dot d : row) {
            if ((notNull && d == null) || (!notNull && d != null)) {
                return false;
            }
        }

        return true;
    }

    private ModelUtil() {
    }
}
