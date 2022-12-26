package com.nuwa.robot.r2022.emotionalability.model;

public class PhaseAnswered {

    private int phaseId;
    private int levelId;
    private int unitId;
    private int isAnswered;

    public PhaseAnswered() {
    }



    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int isAnswered() {
        return isAnswered;
    }

    public void setAnswered(int answered) {
        isAnswered = answered;
    }

    @Override
    public String toString() {
        return "PhaseAnswered{" +
                "phaseId=" + phaseId +
                ", levelId=" + levelId +
                ", unitId=" + unitId +
                ", isAnswered=" + isAnswered +
                '}';
    }
}
