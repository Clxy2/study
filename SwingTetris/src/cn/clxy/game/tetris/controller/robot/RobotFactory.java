package cn.clxy.game.tetris.controller.robot;

import java.util.ArrayList;
import java.util.List;

import cn.clxy.game.tetris.model.ModelUtil;

/**
 * @author clxy
 */
public final class RobotFactory {

    private static List<Robot> robots = new ArrayList<Robot>();
    static {

        Robot r = new RobotDog();
        r.setName("Dog®");
        robots.add(r);

        r = new RobotCat();
        r.setName("Cat®");
        robots.add(r);

        r = new RobotCat();
        r.setName("Pussy®");
        robots.add(r);

        r = new RobotDog();
        r.setName("Puppy®");
        robots.add(r);
    }

    public static Robot getRobot() {

        Robot robot = ModelUtil.getRandom(robots);
        return robot;
    }
}
