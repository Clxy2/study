package cn.clxy.game.uro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.clxy.game.uro.Constant;
import cn.clxy.game.uro.graphics.Cycle;
import cn.clxy.game.uro.graphics.Dimension;

public class Board implements Serializable {

    public Dimension size = new Dimension(100, 100);

    private List<Piece> pieces = new ArrayList<Piece>();

    public enum Position {
        top, right, bottom, left, inner
    }

    public synchronized boolean move(Piece current) {

        Piece nextPiece = current.tryMove();
        Cycle nextCycle = nextPiece.getShape();

        // 1.碰撞检查-墙。
        Position pos = getPosion(nextCycle);
        if (pos != Position.inner) {
            ModelUtil.collision(current, pos);
            return false;
        }

        // 2.碰撞检查-其他图形。
        List<Piece> cs = new ArrayList<Piece>();
        for (Piece piece : pieces) {

            if (current == piece) {
                continue;
            }

            Cycle pcycle = piece.getShape();
            if (nextCycle.getZ() != Constant.COLLISION_Z || nextCycle.getZ() != pcycle.getZ()) {
                continue;
            }

            if (nextCycle.intersects(pcycle)) {
                cs.add(piece);
            }
        }

        if (!cs.isEmpty()) {
            ModelUtil.collision(current, cs);
            return false;
        }

        current.getShape().setZ(Constant.COLLISION_Z);// TODO 每次清除Z？！
        current.move();
        return true;
    }

    /**
     * 追加Cycle。不检查碰撞。
     * @param c
     */
    public void add(Piece p) {
        pieces.add(p);
    }

    public void remove(Piece p) {
        pieces.remove(p);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    /**
     * 边框碰撞测试。不考虑半径。
     * @param cycle
     * @return
     */
    private Position getPosion(Cycle cycle) {

        int x = cycle.getX();
        int y = cycle.getY();
        int r = cycle.r;
        Dimension size = getSize();

        if ((y - r) < 0) {
            return Position.top;
        }

        if ((y + r) > size.height) {
            return Position.bottom;
        }

        if ((x - r) < 0) {
            return Position.left;
        }
        if ((x + r) > size.width) {
            return Position.right;
        }

        return Position.inner;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size.setSize(size);
    }

    @Override
    public String toString() {
        return "Board [size=" + size + ", pieces=" + pieces + "]";
    }

    private static final long serialVersionUID = 1L;
}
