package cn.clxy.game.uro.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jdesktop.application.ResourceMap;

import cn.clxy.game.uro.App;
import cn.clxy.game.uro.Constant;
import cn.clxy.game.uro.graphics.Dimension;
import cn.clxy.game.uro.graphics.Point;
import cn.clxy.game.uro.model.Piece;
import cn.clxy.tools.core.swing.SwingUtil;

/**
 * View utils.
 * @author clxy
 */
public final class ViewUtil {

    public static java.awt.Dimension translate(Dimension d) {
        return new java.awt.Dimension(d.width, d.height);
    }

    public static Dimension translate(java.awt.Dimension d) {
        return new Dimension(d.width, d.height);
    }

    public static java.awt.Point translate(Point p) {
        return new java.awt.Point(p.x, p.y);
    }

    public static Point translate(java.awt.Point p) {
        return new Point(p.x, p.y);
    }

    public static PieceView createView(Piece piece) {

        PieceView view = new PieceView(piece);

        if (images == null || images.isEmpty()) {
            return view;
        }

        Image image = images.get(new Random().nextInt(images.size()));
        int r = piece.getShape().r;
        image = scaleImage(image, new Dimension(r, r));
        view.setImg(image);

        return view;
    }

    /**
     * @param image
     * @param size
     * @return
     */
    public static Image scaleImage(Image image, Dimension size) {

        int width = size.width;
        int height = size.height;

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = result.createGraphics();

        // Low performance.
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2.drawImage(image, 0, 0, width, height, null);
        // g2.setColor(Color.yellow);
        // g2.drawRect(0, 0, width, height);
        g2.dispose();

        return result;
    }

    private static List<Image> images = new ArrayList<Image>();

    static {
        ResourceMap rm = SwingUtil.getResource(App.class, ViewUtil.class);
        images = new ArrayList<Image>();
        for (int i = 1, l = Constant.MAX_TRY; i < l; i++) {

            Image image = SwingUtil.getImageBy(rm, "Orb_Image_00" + i);
            if (image == null) {
                break;
            }
            images.add(image);
        }
    }

    private ViewUtil() {
    }

}
