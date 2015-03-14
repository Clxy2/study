package cn.clxy.game.uro.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import cn.clxy.game.uro.model.Piece;

public class PieceView {

    private Piece piece;
    private Image img;
    private boolean shining;

    public PieceView(Piece piece) {
        this.piece = piece;
    }

    public void paint(Graphics g) {

        if (piece == null) {
            return;
        }

        Graphics2D g2D = (Graphics2D) g;

        int r = piece.getShape().r;
        int x = piece.getLocation().x - r;
        int y = piece.getLocation().y - r;

        if (img != null) {
            g2D.drawImage(img, x, y, img.getWidth(null), img.getHeight(null), null);
            return;
        }

        g2D.setColor(shining ? Color.white : Color.DARK_GRAY);
        g2D.setStroke(new BasicStroke(5f));
        g2D.drawArc(x, y, 2*r, 2*r, 0, 360);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public boolean isShining() {
        return shining;
    }

    public void setShining(boolean shining) {
        this.shining = shining;
    }
}
