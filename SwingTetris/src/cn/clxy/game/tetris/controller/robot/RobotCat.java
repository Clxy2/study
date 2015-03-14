package cn.clxy.game.tetris.controller.robot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.clxy.game.tetris.controller.ActionType;
import cn.clxy.game.tetris.model.Block;
import cn.clxy.game.tetris.model.ModelUtil;
import cn.clxy.game.tetris.model.Stack;
import cn.clxy.game.tetris.view.StackPanel;

/**
 * @author clxy
 */
public class RobotCat extends Robot {

    private static boolean isDanger;

    @Override
    protected RobotTask createTask() {
        return new CatRobotTask();
    }

    private class CatRobotTask extends RobotTask {

        @Override
        protected void think() {

            setMessage("thinking...");
            Date start = new Date();

            actions = new RobotActions();
            StackPanel sv = game.getStackView();
            Stack stack = sv.getStack();
            Block block = sv.getBlock();

            // Current point.
            judgeActions(stack, block);

            setMessage("figure out. @ " + (new Date().getTime() - start.getTime()) + "ms.");
            System.out.println(actions);
        }

        @Override
        protected void findBestAction() {
            isDanger = false;
            for (RobotAction ra : actions.getActions()) {
                if (Boolean.valueOf(true).hashCode() == ra.get("isDanger")) {
                    isDanger = true;
                }
            }
            setMessage("isDanger=" + isDanger);

            actions.sort(c);
            action = actions.last();
        }

        private void setAPreAction(int x, Stack stack, Block block, List<ActionType> steps) {
            Stack s = stack.copy();
            Block b = block.preSetLocation(x, 0);

            RobotAction ra = new RobotAction();

            // set point.
            Block tmp = ModelUtil.createShadow(s, b);
            s.merge(tmp);
            ra.put("isDanger", ModelUtil.isDanger(stack).hashCode());
            ra.setHeight(tmp.getLocation().y);
            ra.setUnion(ModelUtil.countUnions(s, tmp));
            int line = s.clearRow();
            s.zipRow();
            ra.setSpot(ModelUtil.countSpots(s, s.getRectangle()));
            ra.setLine(line);

            ///実装未
            ra.copySteps(steps);
            int oo = x - block.getLocation().x;
            if (oo < 0) {
                for (int k = oo; k < 0; k++) {
                    ra.addStep(ActionType.Left);
                }
            }

            if (oo > 0) {
                for (int k = oo; k > 0; k--) {
                    ra.addStep(ActionType.Right);
                }
            }

            actions.add(ra);
        }

        private void setLinePreActions(int w, Stack stack, Block block, List<ActionType> steps) {
            for (int i = 0; i < w - block.getSize().width + 1; i++) {
                setAPreAction(i, stack, block, steps);
            }
        }

        private void judgeActions(Stack stack, Block block) {
            int width = stack.getSize().width;
            int r = block.getStepIndex();
            List<ActionType> steps = new ArrayList<ActionType>();
            Block newblock = block.copy();

            while (true) {
                checkStop();
                setLinePreActions(width, stack, newblock, steps);

                if (stack.copy().isCollide(newblock.preRotate())) {
                    break;
                }
                newblock.rotate();

                if (newblock.getStepIndex() == r) {// 一回り
                    break;
                }
                steps.add(ActionType.Rotate);
            }
        }
    }
    private static final Comparator<RobotAction> c = new Comparator<RobotAction>() {

        @Override
        public int compare(RobotAction a1, RobotAction a2) {

            int line1 = a1.getLine();
            int line2 = a2.getLine();
            int height1 = a1.getHeight();
            int height2 = a2.getHeight();
            if ((line1 >= 2 || line2 >= 2) && (line1 != line2)) {
                return line1 - line2;
            }
            if (isDanger) {
                if ((line1 >= 1 || line2 >= 1) && (line1 != line2)) {
                    return line1 - line2;
                }
                if (height1 != height2) {
                    return height1 - height2;
                }

            }

            int spot1 = a1.getSpot();
            int spot2 = a2.getSpot();
            if (spot1 != spot2) {
                return spot2 - spot1;
            }

            int union1 = a1.getUnion();
            int union2 = a2.getUnion();
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
