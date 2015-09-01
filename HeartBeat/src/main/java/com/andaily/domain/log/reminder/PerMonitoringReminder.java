package com.andaily.domain.log.reminder;

import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.log.MonitoringReminderLog;
import com.andaily.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * @author Shengzhao Li
 */
public class PerMonitoringReminder extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerMonitoringReminder.class);

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private final FrequencyMonitorLog monitorLog;

    public PerMonitoringReminder(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
    }

    public void remind() {
        this.run();
    }

    @Override
    public void run() {

        if (!isNeedReminder()) {
            LOGGER.debug("Ignore Monitoring Reminder of FrequencyMonitorLog[{}]", monitorLog);
            return;
        }

        MonitoringReminderLog reminderLog = sendAndGenerateReminderLog();

        logRepository.saveOrUpdate(reminderLog);
        LOGGER.debug("Save MonitoringReminderLog[{}]", reminderLog);
    }

    private MonitoringReminderLog sendAndGenerateReminderLog() {
        PerMonitoringReminderSenderResolver senderResolver = new PerMonitoringReminderSenderResolver(monitorLog);
        List<PerMonitoringReminderSender> senders = senderResolver.resolve();
        LOGGER.debug("Resolver [{}] PerMonitoringReminderSenders as follow: {}", senders.size(), senders);

        MonitoringReminderLog reminderLog = new MonitoringReminderLog(monitorLog);
        for (PerMonitoringReminderSender sender : senders) {
            sender.send(reminderLog, monitorLog);
        }
        return reminderLog;
    }

    private boolean isNeedReminder() {
        PerMonitoringReminderChecker reminderChecker = new PerMonitoringReminderChecker(monitorLog);
        return reminderChecker.isNeedReminder();
    }
}