package cn.clxy.game.tetris.controller;

import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import cn.clxy.game.tetris.model.ScoreList;

/**
 * Game by player.
 * @author clxy
 */
public class PlayerGame extends Game {

    public void start() {

        stackView.requestFocus();
        stackView.addKeyListener(kl);

        super.start();
    }

    public void over() {

        super.over();
        stackView.removeKeyListener(kl);

        if (JOptionPane.showConfirmDialog(null,
                "Do you want save this score?\r\nClick yes to save.", "Save Score",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        String name = JOptionPane.showInputDialog("Please input your name.");
        scoreView.setScoreName(name);

        ScoreList.addScore(scoreView.getScore());
        ScoreList.save();
    }

    private KeyListener kl = new TetrisKeyListener(this);
}
