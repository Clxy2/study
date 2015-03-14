package cn.clxy.game.tetris.controller;

import java.awt.event.KeyListener;

import cn.clxy.game.tetris.controller.robot.Robot;
import cn.clxy.game.tetris.controller.robot.RobotFactory;
import cn.clxy.game.tetris.model.ScoreList;

public class RobotGame extends Game {

    private Robot robot;

    public void start() {

        stackView.requestFocus();
        stackView.addKeyListener(kl);

        super.start();

        scoreView.setScoreName(robot.getName());
    }

    public RobotGame() {

        super();

        robot = RobotFactory.getRobot();
        robot.setGame(this);
    }

    @Override
    synchronized void setNextBlock() {

        super.setNextBlock();
        robot.restart();
    }

    @Override
    public void over() {

        robot.stop();
        stackView.removeKeyListener(kl);
        super.over();

        ScoreList.addScore(scoreView.getScore());
        ScoreList.save();
    }

    private KeyListener kl = new TetrisKeyListener(this);
}
