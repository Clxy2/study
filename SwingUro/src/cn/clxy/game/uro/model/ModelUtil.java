package cn.clxy.game.uro.model;

import java.util.List;
import java.util.Random;

import cn.clxy.game.uro.graphics.Point;
import cn.clxy.game.uro.model.Board.Position;

/**
 * 碰撞逻辑参考。 <br/>
 * http://www.ntu.edu.sg/home/ehchua/programming/java/J8a_GameIntro-BouncingBalls.html
 * @author clxy
 */
public final class ModelUtil {

    private static final int[] random_r = { 50, 100, 150 };
    private static final int[] random_v = { 10, 20, 45 };
    private static final double[] random_angle = { -Math.PI / 3, -Math.PI / 4, -Math.PI / 6 };

    public static Piece createRandomPiece() {
        return new Piece(random(random_r), random(random_v), random(random_angle));
    }

    public static void collision(Piece p1, List<Piece> ps) {
        for (Piece p2 : ps) {
            collision(p1, p2);
        }
    }

    public static void collision(Piece p1, Piece p2) {

        Point loc1 = p1.getLocation();
        Point loc2 = p2.getLocation();
        double lineAngle = Math.atan2(loc2.y - loc1.y, loc2.x - loc1.x);

        Rotater r1 = new Rotater(p1.vx, p1.vy, lineAngle);
        double v1p = r1.getVP();
        double v1n = r1.getVN();

        Rotater r2 = new Rotater(p2.vx, p2.vy, lineAngle);
        double v2p = r2.getVP();
        double v2n = r2.getVN();

        int mass1 = p1.getMass();
        int mass2 = p2.getMass();
        int diffMass = mass1 - mass2;
        int sumMass = mass1 + mass2;

        double v1pAfter = (diffMass * v1p + 2.0 * mass2 * v2p) / sumMass;
        double v2pAfter = (2.0 * mass1 * v1p - diffMass * v2p) / sumMass;

        Rotater r1After = new Rotater(v1pAfter, v1n, -lineAngle);
        p1.vx = r1After.getVP();
        p1.vy = r1After.getVN();

        Rotater r2After = new Rotater(v2pAfter, v2n, -lineAngle);
        p2.vx = r2After.getVP();
        p2.vy = r2After.getVN();
    }

    public static void collision(Piece p1, Position pos) {

        switch (pos) {
        case bottom:
        case top:
            p1.vy *= -1;
            break;
        case left:
        case right:
            p1.vx *= -1;
            break;
        }
    }

    private static class Rotater {

        public double vx;
        public double vy;
        public double angle;

        public Rotater(double vx, double vy, double angle) {
            this.vx = vx;
            this.vy = vy;
            this.angle = angle;
        }

        public double getVP() {
            return vx * Math.cos(angle) + vy * Math.sin(angle);
        }

        public double getVN() {
            return -vx * Math.sin(angle) + vy * Math.cos(angle);
        }
    }

    public static int random(int[] is) {
        return is[new Random().nextInt(is.length)];
    }

    public static double random(double[] is) {
        return is[new Random().nextInt(is.length)];
    }

    private ModelUtil() {
    }
}
