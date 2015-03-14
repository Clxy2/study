package cn.clxy.game.tetris.model;

import cn.clxy.game.tetris.graphics.Point;

/**
 * Block.
 * @author clxy
 */
public class Block extends Tetris {

    /**
     * index of rotating.
     */
    private int stepIndex = 0;

    public Block copy() {

        Block b = new Block();
        b.setName(name);
        b.setStepIndex(stepIndex);
        b.setLocation(getLocation());
        b.setData(ModelUtil.copyData(this));

        return b;
    }

    public Block preSetLocation(int x, int y) {

        Block result = copy();
        result.setLocation(x, y);
        return result;
    }

    public Block preSetLocation(Point loc) {
        return preSetLocation(loc.x, loc.y);
    }

    public Block preRotate() {

        Blocks bs = Theme.getBlocksBy(name);
        return bs.getNextBlock(this);
    }

    public void rotate() {

        Block rotated = preRotate();
        setLocation(rotated.getLocation());
        data = rotated.getData();
        stepIndex = ((Block) rotated).getStepIndex();
    }

    public String toString() {

        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", ").append(stepIndex);
        return sb.toString();
    }

    public int getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    private static final long serialVersionUID = 1L;
}
