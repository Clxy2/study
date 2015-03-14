package cn.clxy.game.tetris.controller.robot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RobotActions {

    private List<RobotAction> actions = new ArrayList<RobotAction>();

    public boolean isEmpty() {
        return actions.isEmpty();
    }

    public void addAll(Collection<RobotAction> ras) {
        actions.addAll(ras);
    }

    public void add(RobotAction ra) {
        actions.add(ra);
    }

    public RobotAction first() {

        if (isEmpty()) {
            return null;
        }
        return actions.get(0);
    }

    public RobotAction last() {

        if (isEmpty()) {
            return null;
        }

        return actions.get(actions.size() - 1);
    }

    public void sort(Comparator<RobotAction> c) {
        Collections.sort(actions, c);
    }

    public List<RobotAction> getActions() {
        return actions;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("RobotActions [\r\n");
        for (RobotAction ra : actions) {
            sb.append(ra).append("\r\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
