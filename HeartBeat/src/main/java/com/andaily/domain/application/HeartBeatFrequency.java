package com.andaily.domain.application;

/**
 * 心跳检测频率
 *
 * @author Shengzhao Li
 */

public enum HeartBeatFrequency {
    FIVE(5, "0/5 * * * * ?"),
    TEN(10, "0/10 * * * * ?"),
    TWENTY(20, "0/20 * * * * ?"),
    THIRTY(30, "0/30 * * * * ?"),
    SIXTY(60, "0 0/1 * * * ?");


    private int seconds;
    private String cronExpression;

    private HeartBeatFrequency(int seconds, String cronExpression) {
        this.seconds = seconds;
        this.cronExpression = cronExpression;
    }

    public int getSeconds() {
        return seconds;
    }

    // second -> milli second
    public int getMilliSeconds() {
        return seconds * 1000;
    }

    public String getValue() {
        return name();
    }

    public String getCronExpression() {
        return cronExpression;
    }
}