package com.ansxuman.rootpine.data.model;

public class RootCheckResult {
    private final CheckType checkType;
    private final boolean detected;
    private final String details;

    public RootCheckResult(CheckType checkType, boolean detected, String details) {
        this.checkType = checkType;
        this.detected = detected;
        this.details = details;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public boolean isDetected() {
        return detected;
    }

    public String getDetails() {
        return details;
    }
}