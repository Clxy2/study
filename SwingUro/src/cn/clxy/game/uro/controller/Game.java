package cn.clxy.game.uro.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.clxy.game.uro.Constant;
import cn.clxy.game.uro.graphics.Shape;
import cn.clxy.game.uro.model.Board;
import cn.clxy.game.uro.model.ModelUtil;
import cn.clxy.game.uro.model.Piece;
import cn.clxy.game.uro.view.BoardView;
import cn.clxy.game.uro.view.PieceView;
import cn.clxy.tools.core.Messenger;

/**
 * Game.
 * @author clxy
 */
public class Game {

    protected Timer timer;
    protected boolean running = false;
    private Board board;
    private BoardView boardView;

    private List<PieceTask> pieceTasks;

    public Game() {

        board = new Board();
        boardView = new BoardView(board);
        pieceTasks = new ArrayList<PieceTask>();

        for (int i = 0, l = Constant.COUNT_PIECE; i < l; i++) {
            addPiece();
        }

        boardView.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() != 2) {
                    return;
                }

                PieceView pieceView = boardView.getViewAt(e);
                if (pieceView != null) {
                    removePiece(pieceView.getPiece());
                }
            }
        });

        setMessage(board.toString());

    }

    public synchronized void start() {

        reschedule();
        running = true;
    }

    public synchronized void end() {

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        // 终了处理。
        timer = new Timer(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

            }
        }, 0, 40);

        setMessage("Game over.");
        running = false;
    }

    public void pause() {

        running = false;
        for (PieceTask pt : pieceTasks) {
            pt.setPause(true);
        }

        setMessage("Paused.");
    }

    public void resume() {

        running = true;
        for (PieceTask pt : pieceTasks) {
            pt.setPause(false);
        }

        setMessage("Resumed.");
    }

    private synchronized void reschedule() {

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        timer = new Timer(true);
        for (PieceTask pt : pieceTasks) {
            double v = pt.getPiece().getV();
            timer.schedule(pt, 0, (long) (1000 / v));
        }
    }

    public void setMessage(String msg) {
        messenger.say(msg);
    }

    protected Messenger messenger = Messenger.Default;

    private void addPiece() {

        Piece piece = ModelUtil.createRandomPiece();

        boardView.add(piece);
        pieceTasks.add(new PieceTask(piece, boardView));

        Shape shape = piece.getShape();
        shape.setLocation(shape.getSize().width, shape.getSize().height);
        shape.setZ(99);
    }

    private synchronized void removePiece(Piece piece) {

        boardView.remove(piece);
        for (PieceTask pt : pieceTasks) {
            if (pt.getPiece() == piece) {
                pt.cancel();
                pieceTasks.remove(pt);
            }
        }
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public boolean isRunning() {
        return running;
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
