package cn.clxy.game.tetris.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.jdesktop.application.ResourceMap;

import cn.clxy.game.tetris.App;
import cn.clxy.game.tetris.graphics.Dimension;
import cn.clxy.game.tetris.graphics.Point;
import cn.clxy.game.tetris.model.Dot;
import cn.clxy.game.tetris.model.Tetris;
import cn.clxy.game.tetris.model.Theme;
import cn.clxy.game.tetris.util.SwingUtil;

/**
 * View utils.
 * @author clxy
 */
public final class ViewUtil {

    public static java.awt.Dimension translate(Dimension d) {
        return new java.awt.Dimension(d.width, d.height);
    }

    public static java.awt.Point translate(Point p) {
        return new java.awt.Point(p.x, p.y);
    }

    /**
     * @param g
     * @param tetris
     * @param drs image or shadow.
     */
    public static void drawTetris(Graphics g, Tetris tetris, Draws drs) {

        if (tetris == null) {
            return;
        }
        drawTetris(g, tetris, drs, translate(tetris.getLocation()));
    }

    /**
     * @param g
     * @param tetris
     * @param drs image or shadow.
     * @param loc location.
     */
    public static void drawTetris(Graphics g, Tetris tetris, Draws drs, java.awt.Point loc) {

        if (tetris == null) {
            return;
        }

        int dw = Theme.DOT_SIZE.width;
        int dh = Theme.DOT_SIZE.height;

        Dot[][] data = tetris.getData();

        for (int i = 0, h = data.length; i < h; i++) {
            for (int j = 0, w = data[i].length; j < w; j++) {

                Dot d = data[i][j];
                if (d == null) {
                    continue;
                }

                Image image = null;
                switch (drs) {
                case Image:
                    image = getImageBy(d);
                    break;
                case Shadow:
                    image = getShadowBy(d);
                    break;
                }
                g.drawImage(image, (loc.x + j) * dw, (loc.y + i) * dh, null);
            }
        }
    }

    /**
     * @author clxy
     */
    public static enum Draws {
        Image, Shadow
    }

    /**
     * @param color
     * @param size
     * @return
     */
    public static Image createShadowBy(Color color, Dimension size) {
        return createImageBy(color, size, Draws.Shadow);
    }

    /**
     * @param color
     * @param size
     * @return
     */
    public static Image createImageBy(Color color, Dimension size) {
        return createImageBy(color, size, Draws.Image);
    }

    /**
     * @param color
     * @param size
     * @param ds
     * @return
     */
    public static Image createImageBy(Color color, Dimension size, Draws ds) {

        int dw = size.width;
        int dh = size.height;
        BufferedImage image = new BufferedImage(dw, dh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(color);

        switch (ds) {
        case Image:
            g.fill3DRect(1, 1, dw - 1, dh - 1, true);
            g.draw3DRect(1, 1, dw - 1, dh - 1, true);
            break;
        case Shadow:
            g.draw3DRect(1, 1, dw - 2, dh - 2, true);
            break;
        }

        return image;
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
        g2.dispose();

        return result;
    }

    /**
     * TODO 目标：可以选择不同的Theme。
     */
    public static void setTheme() {

        ResourceMap resource = SwingUtil.getResource(App.class, Theme.class);
        for (String name : Theme.getBlockNames()) {
            imageMap.put(name, createImage(name, resource));
        }
    }

    public static Image getImageBy(Dot d) {

        String name = d.getName();
        ImageInfo ii = imageMap.get(name);
        if (ii == null) {
            new Throwable().printStackTrace();
            return null;
        }
        return ii.getImage();
    }

    public static Image getShadowBy(Dot d) {

        String name = d.getName();
        ImageInfo ii = imageMap.get(name);
        if (ii == null) {
            return null;
        }
        return ii.getShadow();
    }

    private static final Map<String, ImageInfo> imageMap = new HashMap<String, ImageInfo>();

    private ViewUtil() {
    }

    /**
     * @param rm
     * @param key
     * @return
     */
    private static Image loadImageBy(ResourceMap rm, String key) {

        try {
            ImageIcon ii = rm.getImageIcon(key);
            if (ii == null) {
                return null;
            }
            return ViewUtil.scaleImage(ii.getImage(), Theme.DOT_SIZE);
        } catch (Exception e) {
            // do nothing.
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param s
     * @param resource
     * @return
     */
    private static ImageInfo createImage(String s, ResourceMap resource) {

        Color color = resource.getColor(s + ".color");
        if (color == null) {
            color = Color.blue;
        }

        Image image = loadImageBy(resource, s + ".image");
        if (image == null) {
            image = ViewUtil.createImageBy(color, Theme.DOT_SIZE);
        }

        Image shadow = loadImageBy(resource, s + ".shadow");
        if (shadow == null) {
            shadow = ViewUtil.createShadowBy(color, Theme.DOT_SIZE);
        }

        return new ImageInfo(color, image, shadow);
    }

    /**
     * @author clxy
     */
    private static class ImageInfo {

        // private Color color;
        private Image image;
        private Image shadow;

        public ImageInfo(Color color, Image image, Image shadow) {
            // this.color = color;
            this.image = image;
            this.shadow = shadow;
        }

        public Image getImage() {
            return image;
        }

        public Image getShadow() {
            return shadow;
        }
    }
}
