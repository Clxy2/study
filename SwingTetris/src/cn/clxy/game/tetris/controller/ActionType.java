package cn.clxy.game.tetris.controller;

import java.util.ArrayList;
import java.util.List;

import cn.clxy.game.tetris.graphics.Point;

/**
 * @author clxy
 */
public enum ActionType {

    // horizontal
    Right, Left,
    // vertical
    Up, Down,
    // Pause
    Pause,
    // Rotation
    Rotate;

    public void action(Game game) {

        switch (this) {
        case Right:
            game.move(1, 0);
            break;
        case Left:
            game.move(-1, 0);
            break;
        case Rotate:
            game.rotate();
            break;
        case Down:
            game.down();
            break;
        case Up:
            game.up();
            break;
        case Pause:
            game.pause();
            break;
        }
    }

    public void offset(Point p) {

        switch (this) {
        case Right:
            p.offset(1, 0);
            break;
        case Left:
            p.offset(-1, 0);
            break;
        case Rotate:
            break;
        case Down:
            p.offset(0, 1);
            break;
        }
    }

    public static List<ActionType> copy(List<ActionType> steps) {
        return new ArrayList<ActionType>(steps);
    }
}
