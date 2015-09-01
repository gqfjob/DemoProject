package com.andaily.domain.log.reminder;

import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.shared.BeanProvider;

/**
 * @author Shengzhao Li
 */
public class PerMonitoringReminderChecker {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private final FrequencyMonitorLog monitorLog;

    public PerMonitoringReminderChecker(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
    }


    /**
     * If the last FrequencyMonitorLog normal == current normal, not need reminder,
     * If the last is null, check current normal is false or not
     * otherwise, need reminder
     *
     * @return True is need, otherwise false
     */
    public boolean isNeedReminder() {
        FrequencyMonitorLog lastLog = logRepository.findLastLogByCurrentLog(monitorLog);
        if (lastLog == null) {
            return !monitorLog.normal();
        } else {
            return lastLog.normal() != monitorLog.normal();
        }
    }
}