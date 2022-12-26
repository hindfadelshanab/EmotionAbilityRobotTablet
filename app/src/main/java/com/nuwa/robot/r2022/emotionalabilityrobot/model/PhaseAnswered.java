package com.nuwa.robot.r2022.emotionalabilityrobot.model;

public class PhaseAnswered {

  private int phaseId ;
  private int levelId ;
  private int unitId ;
  private boolean isAnswered ;
    private String Response;

    public PhaseAnswered() {
    }

    public PhaseAnswered(int phaseId, int levelId, int unitId, boolean isAnswered ) {
        this.phaseId = phaseId;
        this.levelId = levelId;
        this.unitId = unitId;
        this.isAnswered = isAnswered;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
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
