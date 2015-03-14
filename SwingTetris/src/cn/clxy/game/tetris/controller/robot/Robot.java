package cn.clxy.game.tetris.controller.robot;

import cn.clxy.game.tetris.controller.ActionType;
import cn.clxy.game.tetris.controller.Game;
import cn.clxy.game.tetris.controller.MessagePrinter;
import cn.clxy.game.tetris.model.Block;

/**
 * @author clxy
 */
public abstract class Robot {

    private String name;
    protected Game game;
    private RobotTask task;

    public void restart() {

        stop();
        task = createTask();
        new Thread(task).start();
    }

    public void stop() {

        if (task == null) {
            return;
        }

        task.stop();
    }

    protected abstract RobotTask createTask();

    /**
     * @author clxy
     */
    protected abstract class RobotTask implements Runnable {

        private boolean stop;
        protected RobotAction action;
        protected RobotActions actions;

        public void run() {

            try {

                // Wait one interval for auto down.
                Thread.sleep(game.getInterval());
                checkStop();
                think();
                checkStop();
                findBestAction();
                move();
            } catch (RobotStopedException rse) {
                setMessage(rse.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            stop = true;
        }

        protected abstract void think();

        protected abstract void findBestAction();

        protected void move() throws InterruptedException {

            setMessage("moving...");

            if (action == null) {
                setMessage("don't  know what to do.");
                return;
            }

            // move.
            for (ActionType step : action.getSteps()) {
                checkStop();
                step.action(game);
                Thread.sleep(interval);
            }

            // down.
            Block b = game.getStackView().getBlock();
            int height = action.getHeight();
            while (true) {
                if ((height - b.getLocation().y) <= 1) {
                    break;
                }
                game.down();
                Thread.sleep(interval);
            }

            setMessage("has done.");
        }

        protected void checkStop() {
            if (stop) {
                throw new RobotStopedException();
            }
        }

        protected static final int interval = 150;
    }

    protected void setMessage(String msg) {
        messagePrinter.print(name + "'s " + msg);
    }

    private MessagePrinter messagePrinter = MessagePrinter.DefaultPrinter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private static class RobotStopedException extends RuntimeException {

        public RobotStopedException() {
            super("stoped.");
        }

        private static final long serialVersionUID = 1L;
    }
}
