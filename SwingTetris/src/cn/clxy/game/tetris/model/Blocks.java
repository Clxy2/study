package cn.clxy.game.tetris.model;

import java.util.ArrayList;
import java.util.List;

import cn.clxy.game.tetris.graphics.Dimension;
import cn.clxy.game.tetris.graphics.Point;

/**
 * @author clxy
 */
public class Blocks {

    private String name;
    private List<Tetris> list = new ArrayList<Tetris>();

    public Blocks() {
    }

    public Blocks(String name) {
        this.name = name;
    }

    /**
     * Get next block by step index.
     * @param b
     * @return
     */
    public Block getNextBlock(Block b) {

        int stepIndex = b.getStepIndex();
        stepIndex++;
        if (stepIndex >= list.size()) {
            stepIndex = 0;
        }

        Tetris nextStep = list.get(stepIndex);
        Block result = ((Block) nextStep).copy();
        result.setStepIndex(stepIndex);

        // adjust location.
        Point correction = nextStep.getLocation();
        Point loc = b.getLocation();
        loc.offset(correction.x, correction.y);
        result.setLocation(loc);

        return result;
    }

    /**
     * @return
     */
    public Dimension getMaxSize() {

        int maxWidth = 1;
        int maxheight = 1;

        for (Tetris t : list) {
            Dimension d = t.getSize();
            maxWidth = Math.max(maxWidth, d.width);
            maxheight = Math.max(maxheight, d.height);
        }

        return new Dimension(maxWidth, maxheight);
    }

    /**
     * @param x correction on x direction.
     * @param y correction on y direction.
     * @param cs
     */
    public void add(int x, int y, char[][] cs) {

        Block block = new Block();
        block.setLocation(x, y);
        block.setData(ModelUtil.translate(cs));
        block.setName(name);
        list.add(block);
    }

    /**
     * @return
     */
    public Block getFirstBlock() {
        return (Block) list.get(0);
    }
}