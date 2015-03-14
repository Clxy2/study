package cn.clxy.game.tetris.view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import cn.clxy.game.tetris.App;
import cn.clxy.game.tetris.util.SwingUtil;

/**
 * JPanel with background.
 * @author clxy
 */
public class ImagePanel extends JPanel {

    protected Image img;

    public ImagePanel() {

        super();
        ImageIcon ii = SwingUtil.getResource(App.class, getClass()).getImageIcon("background");
        if (ii != null) {
            img = ii.getImage();
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (img == null) {
            return;
        }
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    private static final long serialVersionUID = 1L;
}