package com.andaily.domain.dto.application;

import com.andaily.domain.dto.AbstractDto;

/**
 * @author Shengzhao Li
 */
public class InstanceStatisticsDto extends AbstractDto {

    // Pattern: 1d 23h 2m 23s
    private String normalConnection;

    private String lastNotNormalTime;

    private int normalAmount;
    private int notNormalAmount;

    private int interruptTime;

    private int sendReminderTime;

    public InstanceStatisticsDto() {
    }

    public InstanceStatisticsDto(String guid) {
        super(guid);
    }

    public String getNormalConnection() {
        return normalConnection;
    }

    public void setNormalConnection(String normalConnection) {
        this.normalConnection = normalConnection;
    }

    public String getLastNotNormalTime() {
        return lastNotNormalTime;
    }

    public void setLastNotNormalTime(String lastNotNormalTime) {
        this.lastNotNormalTime = lastNotNormalTime;
    }

    public int getNormalAmount() {
        return normalAmount;
    }

    public void setNormalAmount(int normalAmount) {
        this.normalAmount = normalAmount;
    }

    public int getNotNormalAmount() {
        return notNormalAmount;
    }

    public void setNotNormalAmount(int notNormalAmount) {
        this.notNormalAmount = notNormalAmount;
    }

    public int getInterruptTime() {
        return interruptTime;
    }

    public void setInterruptTime(int interruptTime) {
        this.interruptTime = interruptTime;
    }

    public int getSendReminderTime() {
        return sendReminderTime;
    }

    public void setSendReminderTime(int sendReminderTime) {
        this.sendReminderTime = sendReminderTime;
    }
}