package cn.clxy.game.tetris.controller;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import cn.clxy.game.tetris.graphics.Point;
import cn.clxy.game.tetris.model.Block;
import cn.clxy.game.tetris.model.Dot;
import cn.clxy.game.tetris.model.ModelUtil;
import cn.clxy.game.tetris.model.Score;
import cn.clxy.game.tetris.model.Stack;
import cn.clxy.game.tetris.model.Theme;
import cn.clxy.game.tetris.view.BlockPanel;
import cn.clxy.game.tetris.view.ScorePanel;
import cn.clxy.game.tetris.view.SinglePanel;
import cn.clxy.game.tetris.view.StackPanel;

/**
 * Game.
 * @author clxy
 */
public class Game {

    protected StackPanel stackView;
    protected BlockPanel blockView;
    protected ScorePanel scoreView;

    protected int downPoint;
    protected Timer timer;
    protected DownTask downTask;

    protected boolean running = false;

    public void setMainPanel(SinglePanel mainPanel) {

        stackView = mainPanel.getStackPanel();
        blockView = mainPanel.getBlockPanel();
        scoreView = mainPanel.getScorePanel();
    }

    public void start() {

        Block block = Theme.getNextBlock();
        Stack stack = Theme.createStack();
        blockView.setBlock(block);
        stackView.setStack(stack);
        setNextBlock();

        scoreView.start();
        reschedule(scoreView.getScore().getInterval());

        running = true;
    }

    public void over() {

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        stackView.setBlock(null);
        stackView.setShadow(null);

        scoreView.end();

        timer = new Timer(true);
        timer.schedule(new TimerTask() {

            private int i = stackView.getStack().getSize().height - 1;
            private final Dot o = new Dot(Theme.OVER);

            @Override
            public void run() {

                Dot[] row = stackView.getStack().getData()[i];
                Arrays.fill(row, o);
                stackView.repaint();

                i--;
                if (i < 0) {
                    timer.cancel();
                }
            }
        }, 0, 40);

        setMessage("Game over.");
        running = false;
    }

    public synchronized void up() {
        move(0, -1);
        scoreView.getScore().addUp();
    }

    public synchronized void down() {
        downPoint++;
        doDown();
    }

    private synchronized void doDown() {

        boolean success = move(0, 1);
        if (success) {
            return;
        }

        boolean isPause = downTask.isPause();
        downTask.setPause(true);

        Stack stack = stackView.getStack();
        Block block = stackView.getBlock();
        stack.merge(block);
        int line = stack.clearRow();
        stack.zipRow();

        // set score.
        Score score = scoreView.getScore();
        int oldLevel = score.getLevel();

        scoreView.addLine(line);
        scoreView.addPoint(downPoint);
        downPoint = 0;

        int ren = score.getRen();
        if (line > 0) {
            String msg = String.format("<html><b><font color='red'>%s</font></b> line(s) clear.",
                    line);
            if (ren > 0) {
                msg += String.format("<b><font color='green'>%s</font></b> ren(s) !", ren);
            }
            setMessage(msg);
        }

        setNextBlock();

        // Game over.
        if (stackView.getStack().isCollide(stackView.getBlock())) {
            over();
            return;
        }

        int newLevel = score.getLevel();
        if (newLevel > oldLevel) {
            setMessage("Level Up!");
            reschedule(score.getInterval());
        }

        downTask.setPause(isPause);
    }

    public synchronized void rotate() {

        Stack stack = stackView.getStack();
        Block block = stackView.getBlock();

        Block pb = block.preRotate();
        if (stack.isCollide(pb)) {
            return;
        }

        block.rotate();
        stackView.setShadow(ModelUtil.createShadow(stack, block));
        stackView.repaint();
    }

    public synchronized void pause() {

        downTask.setPause(!downTask.isPause());
        scoreView.getScore().addPause();
        setMessage("Paused.");
    }

    public synchronized boolean move(int x, int y) {

        Stack stack = stackView.getStack();
        Block block = (Block) stackView.getBlock();

        if (block == null) {
            return true;
        }

        Point loc = block.getLocation();
        Block pb = block.preSetLocation(loc.x + x, loc.y + y);

        if (stack.isCollide(pb)) {
            return false;
        }

        block.setLocation(pb.getLocation());
        stackView.setShadow(ModelUtil.createShadow(stack, block));
        stackView.repaint();
        return true;
    }

    synchronized void setNextBlock() {

        stackView.setBlock(blockView.getBlock());
        blockView.setBlock(Theme.getNextBlock());
        stackView.setShadow(ModelUtil.createShadow(stackView.getStack(), stackView.getBlock()));
    }

    private synchronized void reschedule(int interval) {

        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer(true);
        downTask = new DownTask();
        timer.schedule(downTask, 0, interval);
    }

    public void setMessage(String msg) {
        messagePrinter.print(msg);
    }

    protected MessagePrinter messagePrinter = MessagePrinter.DefaultPrinter;

    private class DownTask extends PausableTask {

        @Override
        public void doRun() {
            doDown();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setMessagePrinter(MessagePrinter mp) {
        this.messagePrinter = mp;
    }

    public StackPanel getStackView() {
        return stackView;
    }

    public int getInterval() {
        return scoreView.getScore().getInterval();
    }
}
