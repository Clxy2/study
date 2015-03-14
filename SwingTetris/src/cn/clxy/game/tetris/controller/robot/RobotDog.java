package cn.clxy.game.tetris.controller.robot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.clxy.game.tetris.controller.ActionType;
import cn.clxy.game.tetris.graphics.Point;
import cn.clxy.game.tetris.graphics.Rectangle;
import cn.clxy.game.tetris.model.Block;
import cn.clxy.game.tetris.model.ModelUtil;
import cn.clxy.game.tetris.model.Stack;
import cn.clxy.game.tetris.view.StackPanel;

/**
 * @author clxy
 */
public class RobotDog extends Robot {

    @Override
    protected RobotTask createTask() {
        return new DogRobotTask();
    }

    private class DogRobotTask extends RobotTask {

        @Override
        protected void think() {

            setMessage("thinking...");
            Date start = new Date();

            actions = new RobotActions();
            StackPanel sv = game.getStackView();
            Stack stack = sv.getStack();
            Block block = sv.getBlock();

            // Current point.
            List<ActionType> steps = new ArrayList<ActionType>();
            judge(stack, block, steps);
            // Right.
            go(stack, block, ActionType.Right, steps);
            // Left
            go(stack, block, ActionType.Left, steps);

            setMessage("figure out. @ " + (new Date().getTime() - start.getTime()) + "ms.");
        }

        @Override
        protected void findBestAction() {
            actions.sort(c);
            action = actions.last();
        }

        private void go(Stack s, Block block, ActionType t, List<ActionType> steps) {

            Block b = block.copy();
            List<ActionType> ss = ActionType.copy(steps);

            while (true) {

                checkStop();

                if (tryGo(s, b, t, ss)) {
                    continue;
                }

                int r = b.getStepIndex();
                while (true) {

                    Block rb = b.preRotate();
                    if (r == rb.getStepIndex() || s.isCollide(rb)) {
                        // 一回り or ぶつかる。
                        return;
                    }

                    b.rotate();
                    ss.add(ActionType.Rotate);
                    if (tryGo(s, rb, t, ss)) {
                        break;
                    }
                }
            }
        }

        private boolean tryGo(Stack s, Block b, ActionType t, List<ActionType> ss) {

            Point loc = b.getLocation();
            t.offset(loc);
            if (s.isCollide(b.preSetLocation(loc))) {
                return false;
            }

            b.setLocation(loc);
            ss.add(t);
            judge(s, b, ss);
            return true;
        }

        private void judge(Stack stack, Block block, List<ActionType> steps) {

            Block b = block.copy();
            List<ActionType> ss = ActionType.copy(steps);

            int r = b.getStepIndex();

            while (true) {

                checkStop();
                Stack copyStack = stack.copy();

                RobotAction ra = new RobotAction();
                // set point.
                Block shw = ModelUtil.createShadow(copyStack, b);
                copyStack.merge(shw);
                ra.setHeight(shw.getLocation().y);
                ra.setUnion(ModelUtil.countUnions(copyStack, shw));
                int line = copyStack.clearRow();
                ra.setLine(line);

                Rectangle rec = shw.getRectangle();
                if (line != rec.height) {
                    copyStack.zipRow();
                    ra.setSpot(ModelUtil.countSpots(copyStack));
                }

                ra.copySteps(ss);
                actions.add(ra);

                if (stack.isCollide(b.preRotate())) {
                    break;
                }
                b.rotate();
                ss.add(ActionType.Rotate);

                if (b.getStepIndex() == r) {// 一回り
                    break;
                }
            }
        }
    }

    // private static final int limen = 10;

    private static final Comparator<RobotAction> c = new Comparator<RobotAction>() {

        /**
         * <ol>
         * <li>line >=2. more ⇒ good.
         * <li>blank. less ⇒ good.
         * <li>union. more ⇒ good.
         * <li>height. more ⇒ good.
         * <li>line < 2 . more ⇒ good.
         * </ol>
         */
        @Override
        public int compare(RobotAction a1, RobotAction a2) {

            int line1 = a1.getLine();
            int line2 = a2.getLine();
            int spot1 = a1.getSpot();
            int spot2 = a2.getSpot();
            int union1 = a1.getUnion();
            int union2 = a2.getUnion();
            int height1 = a1.getHeight();
            int height2 = a2.getHeight();

            if ((line1 >= 2 || line2 >= 2) && (line1 != line2)) {
                return line1 - line2;
            }

            if (spot1 != spot2) {
                return spot2 - spot1;
            }

            if (union1 != union2) {
                return union1 - union2;
            }

            if (height1 != height2) {
                return height1 - height2;
            }

            if (line1 != line2) {
                return line1 - line2;
            }

            return 0;
        }
    };

}
