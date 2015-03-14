package cn.clxy.game.uro.controller;

import java.util.TimerTask;

/**
 * Task can pause.
 * @author clxy
 */
public abstract class PausableTask extends TimerTask {

    private boolean pause = false;

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    @Override
    public void run() {
        if (pause) {
            return;
        }
        doRun();
    }

    protected abstract void doRun();
}
