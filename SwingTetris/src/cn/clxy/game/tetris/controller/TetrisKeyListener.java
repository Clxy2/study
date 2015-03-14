package cn.clxy.game.tetris.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class TetrisKeyListener implements KeyListener {

    private final Game game;

    public TetrisKeyListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

        case KeyEvent.VK_LEFT:
            game.move(-1, 0);
            break;

        case KeyEvent.VK_RIGHT:
            game.move(1, 0);
            break;

        case KeyEvent.VK_UP:
            game.rotate();
            break;

        case KeyEvent.VK_SPACE:
            game.pause();
            break;

        case KeyEvent.VK_END:
            game.up();
            break;

        case KeyEvent.VK_DOWN:
            game.down();
            break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
