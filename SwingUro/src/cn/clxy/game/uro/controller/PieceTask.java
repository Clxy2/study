package cn.clxy.game.uro.controller;

import cn.clxy.game.uro.model.Piece;
import cn.clxy.game.uro.view.BoardView;

public class PieceTask extends PausableTask {

    private Piece piece;
    private BoardView board;

    public PieceTask(Piece piece, BoardView board) {
        super();
        this.piece = piece;
        this.board = board;
    }

    @Override
    protected void doRun() {

        boolean moved = board.getBoard().move(piece);
        if (moved) {
            board.repaint();
        }
    }

    public Piece getPiece() {
        return piece;
    }
}
