package cn.clxy.game.tetris.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.clxy.game.tetris.graphics.Dimension;

/**
 * @author clxy
 */
public final class Theme {

    public static final Dimension DOT_SIZE = new Dimension(25, 25);
    public static final Dimension STACK_GRID = new Dimension(10, 20);

    /**
     * Create new stack.
     * @return
     */
    public static Stack createStack() {
        return new Stack(new Dot[STACK_GRID.height][STACK_GRID.width]);
    }

    /**
     * Stack size.
     * @return
     */
    public static Dimension getStackSize() {
        return new Dimension(STACK_GRID.width * DOT_SIZE.width, STACK_GRID.height * DOT_SIZE.height);
    }

    /**
     * Max block size.
     * @return
     */
    public static Dimension getMaxTetrisSize() {

        int maxWidth = 1;
        int maxheight = 1;

        for (Blocks bs : blockMap.values()) {

            Dimension d = bs.getMaxSize();
            maxWidth = Math.max(maxWidth, d.width);
            maxheight = Math.max(maxheight, d.height);
        }

        return new Dimension(maxWidth * DOT_SIZE.width, maxheight * DOT_SIZE.height);
    }

    /**
     * Get next block when rotating.
     * @return
     */
    public static Block getNextBlock() {

        // Get random block.
        String name = ModelUtil.getRandom(blockMap.keySet());
        Block result = blockMap.get(name).getFirstBlock().copy();

        // Make it center.
        Dimension size = result.getSize();
        result.setLocation((STACK_GRID.width - size.width) / 2, size.height * -1 + 1);

        return result;
    }

    public static Set<String> getBlockNames() {
        return blockMap.keySet();
    }

    static Blocks getBlocksBy(String name) {
        return blockMap.get(name);
    }

    private static final String I = "I";
    private static final String J = "J";
    private static final String L = "L";
    private static final String O = "O";
    private static final String S = "S";
    private static final String T = "T";
    private static final String Z = "Z";
    public static final String OVER = "Over";

    private static Map<String, Blocks> blockMap = new HashMap<String, Blocks>();
    static {

        // I
        Blocks bs = new Blocks(I);
        blockMap.put(I, bs);
        bs.add(-1, 1, new char[][] { { '■', '■', '■', '■' } });
        bs.add(1, -1, new char[][] { { '■' }, { '■' }, { '■' }, { '■' } });

        // O
        bs = new Blocks(O);
        blockMap.put(O, bs);
        bs.add(0, 0, new char[][] { { '■', '■' }, { '■', '■' } });

        // S
        bs = new Blocks(S);
        blockMap.put(S, bs);
        bs.add(0, 0, new char[][] { { '□', '■', '■' }, { '■', '■', '□' } });
        bs.add(1, 0, new char[][] { { '■', '□' }, { '■', '■' }, { '□', '■' } });
        bs.add(-1, 1, new char[][] { { '□', '■', '■' }, { '■', '■', '□' } });
        bs.add(0, -1, new char[][] { { '■', '□' }, { '■', '■' }, { '□', '■' } });

        // Z
        bs = new Blocks(Z);
        blockMap.put(Z, bs);
        bs.add(0, 0, new char[][] { { '■', '■', '□' }, { '□', '■', '■' } });
        bs.add(1, 0, new char[][] { { '□', '■' }, { '■', '■' }, { '■', '□' } });
        bs.add(-1, 1, new char[][] { { '■', '■', '□' }, { '□', '■', '■' } });
        bs.add(0, -1, new char[][] { { '□', '■' }, { '■', '■' }, { '■', '□' } });

        // T
        bs = new Blocks(T);
        blockMap.put(T, bs);
        bs.add(0, 0, new char[][] { { '□', '■', '□' }, { '■', '■', '■' } });
        bs.add(1, 0, new char[][] { { '■', '□' }, { '■', '■' }, { '■', '□' } });
        bs.add(-1, 1, new char[][] { { '■', '■', '■' }, { '□', '■', '□' } });
        bs.add(0, -1, new char[][] { { '□', '■' }, { '■', '■' }, { '□', '■' } });

        // J
        bs = new Blocks(J);
        blockMap.put(J, bs);
        bs.add(0, 0, new char[][] { { '■', '□', '□' }, { '■', '■', '■' } });
        bs.add(1, 0, new char[][] { { '■', '■' }, { '■', '□' }, { '■', '□' } });
        bs.add(-1, 1, new char[][] { { '■', '■', '■' }, { '□', '□', '■' } });
        bs.add(0, -1, new char[][] { { '□', '■' }, { '□', '■' }, { '■', '■' } });

        // L
        bs = new Blocks(L);
        blockMap.put(L, bs);
        bs.add(-1, 1, new char[][] { { '■', '■', '■' }, { '■', '□', '□' } });
        bs.add(0, -1, new char[][] { { '■', '■' }, { '□', '■' }, { '□', '■' } });
        bs.add(0, 0, new char[][] { { '□', '□', '■' }, { '■', '■', '■' } });
        bs.add(1, 0, new char[][] { { '■', '□' }, { '■', '□' }, { '■', '■' } });

        // Over
        bs = new Blocks(OVER);
        blockMap.put(OVER, bs);
        bs.add(0, 0, new char[][] { { '■' } });
    }
}
