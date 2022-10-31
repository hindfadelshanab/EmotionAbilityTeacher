package com.nuwa.robot.r2022.emotionalability.model;

public class PhaseAnswered {

    int phaseId ;
    int levelId ;
    int unitId ;
    boolean isAnswered ;

    public PhaseAnswered() {
    }

    public PhaseAnswered(int phaseId, int levelId, int unitId, boolean isAnswered) {
        this.phaseId = phaseId;
        this.levelId = levelId;
        this.unitId = unitId;
        this.isAnswered = isAnswered;
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

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
