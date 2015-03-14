package cn.clxy.game.tetris.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Score.
 * @author clxy
 */
public class Score implements Serializable {

    private int line;
    private int point = 0;

    private Date startTime;
    private Date overTime;

    private String name;
    private int upCount;
    private int pauseCount;

    private int successCount;

    public Score() {
    }

    public Score(String name) {

        this.name = name;
        this.startTime = new Date();
    }

    public void addPoint(int p) {
        point += p;
    }

    public void addLine(int line) {

        if (line == 0) {
            successCount = 0;
            return;
        }

        switch (line) {
        case 1:
            point += 100;
            break;
        case 2:
            point += 300;
            break;
        case 3:
            point += 500;
            break;
        case 4:
            point += 800;
            break;
        }

        if (successCount > 1) {
            point += getRen() * 50;
        }

        this.line += line;
        successCount++;
    }

    public int getInterval() {

        int level = getLevel() - 1;
        level = level % interval.length;
        return interval[level];
    }

    public int getRen() {
        return successCount - 1;
    }

    public void addPause() {
        pauseCount++;
    }

    public void addUp() {
        upCount++;
    }

    private static final int rankStep = 5000;
    private static final int[] interval = { 1000, 800, 600, 400, 200 };

    public int getLine() {
        return line;
    }

    public int getPoint() {
        return point;
    }

    public int getLevel() {
        return point / rankStep + 1;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public int getUpCount() {
        return upCount;
    }

    public int getPauseCount() {
        return pauseCount;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public void setPauseCount(int pauseCount) {
        this.pauseCount = pauseCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    private static final long serialVersionUID = 1L;
}
