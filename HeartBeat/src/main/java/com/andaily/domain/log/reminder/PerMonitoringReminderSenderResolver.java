package com.andaily.domain.log.reminder;

import com.andaily.domain.log.FrequencyMonitorLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class PerMonitoringReminderSenderResolver {

    private List<PerMonitoringReminderSender> reminderSenders = new ArrayList<>();
    private FrequencyMonitorLog monitorLog;

    public PerMonitoringReminderSenderResolver(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
        initialSenders();
    }

    private void initialSenders() {
        this.reminderSenders.add(new EmailPerMonitoringReminderSender());
    }

    public List<PerMonitoringReminderSender> resolve() {
        List<PerMonitoringReminderSender> availableSenders = new ArrayList<>();
        for (PerMonitoringReminderSender reminderSender : reminderSenders) {
            if (reminderSender.support(monitorLog)) {
                availableSenders.add(reminderSender);
            }
        }
        return availableSenders;
    }
}