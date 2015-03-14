package cn.clxy.game.tetris.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Score list.
 * @author clxy
 */
public final class ScoreList {

    private static List<Score> list;
    private static final String fileName = "score.data";

    static {
        ObjectInputStream decoder = null;
        try {
            decoder = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            list = (List<Score>) decoder.readObject();
        } catch (Exception e) {
            list = new ArrayList<Score>();
        } finally {
            if (decoder != null) {
                try {
                    decoder.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static List<Score> getList() {
        return list;
    }

    public static void addScore(Score s) {
        list.add(s);
        Collections.sort(list, sc);
    }

    public static void save() {

        ObjectOutputStream encoder = null;
        try {
            encoder = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(fileName)));
            encoder.writeObject(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (encoder != null) {
                try {
                    encoder.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * poing desc, start date desc.
     */
    private static final Comparator<Score> sc = new Comparator<Score>() {

        @Override
        public int compare(Score s1, Score s2) {

            int p1 = s1.getPoint();
            int p2 = s2.getPoint();
            if (p1 != p2) {
                return p2 - p1;
            }

            Date d1 = s1.getStartTime();
            Date d2 = s2.getStartTime();
            return (int) (d2.getTime() - d1.getTime());
        }
    };
}
