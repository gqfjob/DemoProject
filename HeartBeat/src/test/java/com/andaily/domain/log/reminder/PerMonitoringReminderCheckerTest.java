package com.andaily.domain.log.reminder;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.infrastructure.AbstractRepositoryTest;
import com.andaily.infrastructure.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class PerMonitoringReminderCheckerTest extends AbstractRepositoryTest {


    @Autowired
    private LogRepository logRepository;

    @Test
    public void testIsNeedReminder() throws Exception {

        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        //case 1
        PerMonitoringReminderChecker reminderChecker = new PerMonitoringReminderChecker(monitorLog);
        final boolean needReminder = reminderChecker.isNeedReminder();
        assertTrue(needReminder);


        //case 2
        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:10", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog2);

        fullClean();

        reminderChecker = new PerMonitoringReminderChecker(monitorLog);
        assertTrue(reminderChecker.isNeedReminder());

    }
}