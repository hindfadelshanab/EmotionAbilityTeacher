package com.nuwa.robot.r2022.emotionalability.model;

public class MessageExpression {

    private int phaseId;
    private int LevelId;
    private int unitId;
    private int messageId;
    private String EmotionName;
    private String bodyMotionName;
    private String response;


    public MessageExpression() {
    }

    public MessageExpression(int messageId , int phaseId, int levelId, int unitId, String emotionName, String bodyMotionName, String response) {
        this.phaseId = phaseId;
        LevelId = levelId;
        this.unitId = unitId;
        EmotionName = emotionName;
        this.bodyMotionName = bodyMotionName;
        this.response = response;
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getBodyMotionName() {
        return bodyMotionName;
    }

    public void setBodyMotionName(String bodyMotionName) {
        this.bodyMotionName = bodyMotionName;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getEmotionName() {
        return EmotionName;
    }

    public void setEmotionName(String emotionName) {
        EmotionName = emotionName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getLevelId() {
        return LevelId;
    }

    public void setLevelId(int levelId) {
        LevelId = levelId;
    }
}
