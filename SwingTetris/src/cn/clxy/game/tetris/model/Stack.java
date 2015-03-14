package cn.clxy.game.tetris.model;

import java.util.Arrays;

import cn.clxy.game.tetris.graphics.Dimension;
import cn.clxy.game.tetris.graphics.Point;
import cn.clxy.game.tetris.graphics.Rectangle;

/**
 * Stack.
 * @author clxy
 */
public class Stack extends Tetris {

    public Stack() {
        init();
    }

    public Stack(Dot[][] data) {
        init();
        this.data = data;
    }

    public Stack copy() {
        return new Stack(ModelUtil.copyData(this));
    }

    /**
     * Check the collide.
     * <ol>
     * <li>if knock against the stack bounds.</li>
     * <li>if collide.</li>
     * </ol>
     * TODO It seems has bug on Game over.
     * @param t
     * @return
     */
    public boolean isCollide(Tetris t) {

        Point loc = t.getLocation();

        // Check bound.
        Dimension size = t.getSize();
        int y = loc.y;
        int height = size.height;
        // Top is ok.
        if (y < 0) {
            y = 0;
            height += y;
        }

        Rectangle br = new Rectangle(loc.x, y, size.width, height);
        Rectangle r = getRectangle();
        if (!r.contains(br)) {
            return true;
        }

        // Check collide.
        Dot[][] tData = t.getData();
        for (int i = 0, h = tData.length; i < h; i++) {
            for (int j = 0, w = tData[i].length; j < w; j++) {

                int sy = i + loc.y;
                int sx = j + loc.x;
                if (ModelUtil.outBound(data, sy, sx)) {
                    continue;
                }
                if (data[sy][sx] != null && tData[i][j] != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public int clearRow() {

        int result = 0;
        for (Dot[] row : data) {
            if (ModelUtil.isFullRow(row)) {
                Arrays.fill(row, null);
                result++;
            }
        }

        return result;
    }

    public void zipRow() {

        int blankCount = 0;

        for (int i = getSize().height - 1; i >= 0; i--) {

            if (ModelUtil.isBlankRow(data[i])) {
                blankCount++;
                continue;
            }

            if (blankCount == 0) {
                continue;
            }

            ModelUtil.switchRow(data, i, i + blankCount);
        }
    }

    public void merge(Tetris block) {
        data = ModelUtil.mergeData(this, block);
    }

    private void init() {
        name = "stack";
    }

    private static final long serialVersionUID = 1L;
}
