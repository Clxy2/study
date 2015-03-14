package cn.clxy.game.uro.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import cn.clxy.game.uro.graphics.Point;
import cn.clxy.game.uro.model.Board;
import cn.clxy.game.uro.model.Piece;
import cn.clxy.tools.core.swing.BackgroundPanel;

/**
 * @author clxy
 */
public class BoardView extends BackgroundPanel {

    private Board board;
    private PieceView currentView;
    private List<PieceView> views = new ArrayList<PieceView>();

    public BoardView() {
        this(new Board());
    }

    public BoardView(Board board) {

        super();
        this.board = board;
        setPreferredSize(ViewUtil.translate(board.getSize()));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setCurrentView(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                setCurrentView(e);
                // TODO 可以拖动。
            }
        });

        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component) e.getSource();
                resizeBoard(c.getSize());
            }
        });
    }

    public void add(Piece p) {
        board.add(p);
        views.add(ViewUtil.createView(p));
    }

    public void remove(Piece p) {

        if (currentView.getPiece() == p) {
            currentView = null;
        }

        for (PieceView view : views) {
            if (view.getPiece() == p) {
                views.remove(view);
                break;
            }
        }

        board.remove(p);
    }

    private void resizeBoard(Dimension dim) {
        board.setSize(ViewUtil.translate(dim));
    }

    private void setCurrentView(MouseEvent e) {

        PieceView view = getViewAt(e);
        if (view == null || currentView == view) {
            return;
        }

        currentView.setShining(false);
        currentView = view;
        currentView.setShining(true);
    }

    public PieceView getViewAt(MouseEvent e) {

        Point p = ViewUtil.translate(e.getPoint());
        for (PieceView view : views) {
            if (view.getPiece().getShape().contains(p)) {
                return view;
            }
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (views == null) {
            return;
        }

        for (PieceView pv : views) {
            pv.paint(g);
        }
    }

    public Board getBoard() {
        return board;
    }

    private static final long serialVersionUID = 1L;
}
