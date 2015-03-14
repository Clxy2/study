package cn.clxy.game.tetris.controller.robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.clxy.game.tetris.controller.ActionType;

public class RobotAction {

    private List<ActionType> steps;
    private Map<String, Integer> scoreMap = new HashMap<String, Integer>();

    private static final String spotKey = "spot";
    private static final String unionKey = "union";
    private static final String heightKey = "height";
    private static final String lineKey = "line";

    public void addStep(ActionType type) {
        steps.add(type);
    }

    public void put(String key, Integer value) {
        scoreMap.put(key, value);
    }

    public Integer get(String key) {
        return scoreMap.get(key);
    }

    public int getInt(String key) {

        Integer i = scoreMap.get(key);
        return i == null ? 0 : i;
    }

    public void copySteps(List<ActionType> steps) {
        this.steps = ActionType.copy(steps);
    }

    @Override
    public String toString() {
        return "RobotAction [scoreMap=" + scoreMap + ", steps=" + steps + "]";
    }

    public void setLine(int line) {
        put(lineKey, line);
    }

    public void setUnion(int union) {
        put(unionKey, union);
    }

    public int getUnion() {
        return getInt(unionKey);
    }

    public int getLine() {
        return getInt(lineKey);
    }

    public int getHeight() {
        return getInt(heightKey);
    }

    public void setHeight(int height) {
        put(heightKey, height);
    }

    public List<ActionType> getSteps() {
        return steps;
    }

    public int getSpot() {
        return getInt(spotKey);
    }

    public void setSpot(int spot) {
        put(spotKey, spot);
    }
}
