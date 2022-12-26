package com.nuwa.robot.r2022.emotionalabilityrobot.model;

public class MessageExpression {

    private  int phaseId ;
    private  int LevelId ;
    private  int unitId;
    private  int messageId;
    private  String EmotionName ;
    private String response;

    public MessageExpression() {
    }

    public MessageExpression(int phaseId, int levelId, int unitId, String emotionName, String response) {
        this.phaseId = phaseId;
        this.LevelId = levelId;
        this.unitId = unitId;
        this.EmotionName = emotionName;
        this.response = response;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    @Override
    public String toString() {
        return "MessageExpression{" +
                "phaseId=" + phaseId +
                ", LevelId=" + LevelId +
                ", unitId=" + unitId +
                ", EmotionName='" + EmotionName + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
