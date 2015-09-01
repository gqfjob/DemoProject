package com.andaily.domain.log.reminder;

import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.MonitoringReminderLog;

/**
 * @author Shengzhao Li
 */
public interface PerMonitoringReminderSender {


    public boolean support(FrequencyMonitorLog monitorLog);

    public void send(MonitoringReminderLog reminderLog, FrequencyMonitorLog monitorLog);

}