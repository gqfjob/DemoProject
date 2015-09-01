package com.andaily.infrastructure.hibernate;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.log.MonitoringReminderLog;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.infrastructure.AbstractRepositoryTest;
import com.andaily.infrastructure.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * 15-1-2
 *
 * @author Shengzhao Li
 */
public class LogRepositoryHibernateTest extends AbstractRepositoryTest {

    @Autowired
    private LogRepository logRepository;


    @Test
    public void testMonitoringReminderLog() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        MonitoringReminderLog reminderLog = new MonitoringReminderLog(monitorLog);
        logRepository.saveOrUpdate(reminderLog);

        fullClean();

        final MonitoringReminderLog result = logRepository.findByGuid(reminderLog.guid(), MonitoringReminderLog.class);
        assertNotNull(result);
        assertNotNull(result.monitorLog());
        assertNotNull(result.instance());

    }

    @Test
    public void totalMonitoringReminderLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        MonitoringReminderLog reminderLog = new MonitoringReminderLog(monitorLog);
        logRepository.saveOrUpdate(reminderLog);

        fullClean();


        final int amount = logRepository.totalMonitoringReminderLogs(applicationInstance.guid());
        assertEquals(amount, 1);


    }


    @Test
    public void amountOfMonitoringReminderLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        MonitoringReminderLog reminderLog = new MonitoringReminderLog(monitorLog);
        logRepository.saveOrUpdate(reminderLog);

        fullClean();


        final int amount = logRepository.amountOfMonitoringReminderLogs(applicationInstance.guid(), true);
        assertEquals(amount, 0);


    }

    @Test
    public void testFrequencyMonitorLog() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        FrequencyMonitorLog log = logRepository.findByGuid(monitorLog.guid(), FrequencyMonitorLog.class);
        assertNotNull(log);
        assertNotNull(log.instance());
        assertNotNull(log.costTime());
    }

    @Test
    public void findLastLogByCurrentLog() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        final FrequencyMonitorLog log = logRepository.findLastLogByCurrentLog(monitorLog);
        assertNull(log);


        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:10", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog2);

        FrequencyMonitorLog monitorLog3 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:10:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog3);

        fullClean();

        final FrequencyMonitorLog log1 = logRepository.findLastLogByCurrentLog(monitorLog);
        assertNotNull(log1);
        assertEquals(log1.guid(), monitorLog2.guid());

    }

    @Test
    public void findLastNotNormalFrequencyMonitorLog() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog);

        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:25", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog2);

        FrequencyMonitorLog monitorLog3 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:10:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog3);

        FrequencyMonitorLog monitorLog4 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:15:21", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog4);

        fullClean();

        final FrequencyMonitorLog log = logRepository.findLastNotNormalFrequencyMonitorLog(applicationInstance.guid());
        assertNotNull(log);
        assertEquals(log.guid(), monitorLog2.guid());

    }

    @Test
    public void findFirstMonitoringLogTime() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog);

        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:25", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog2);

        FrequencyMonitorLog monitorLog3 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:10:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog3);

        FrequencyMonitorLog monitorLog4 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:15:21", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog4);

        fullClean();

        Date date = logRepository.findFirstMonitoringLogTime(applicationInstance.guid(), true);
        assertNotNull(date);
        assertEquals(date, monitorLog4.createTime());

        Date date2 = logRepository.findFirstMonitoringLogTime(applicationInstance.guid(), false);
        assertNotNull(date2);
        assertEquals(date2, monitorLog3.createTime());

    }

    @Test
    public void findFirstMonitoringLogTimeAfterSpecifyTime() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog);

        final Date date2 = DateUtils.getDate("2014-12-12 12:12:25", DateUtils.DEFAULT_DATE_TIME_FORMAT);
        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(date2);
        logRepository.saveOrUpdate(monitorLog2);

        FrequencyMonitorLog monitorLog3 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:10:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog3);

        final Date date4 = DateUtils.getDate("2014-12-12 12:15:21", DateUtils.DEFAULT_DATE_TIME_FORMAT);
        FrequencyMonitorLog monitorLog4 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(date4);
        logRepository.saveOrUpdate(monitorLog4);

        fullClean();

        Date date = logRepository.findFirstMonitoringLogTimeAfterSpecifyTime(applicationInstance.guid(), date4);
        assertNull(date);

        date = logRepository.findFirstMonitoringLogTimeAfterSpecifyTime(applicationInstance.guid(), date2);
        assertNotNull(date);
        assertEquals(date, date4);

    }

    @Test
    public void amountOfInstanceMonitorLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog);

        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:25", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog2);

        FrequencyMonitorLog monitorLog3 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:10:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog3);

        FrequencyMonitorLog monitorLog4 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:15:21", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog4);

        fullClean();

        final int amount = logRepository.amountOfInstanceMonitorLogs(applicationInstance.guid(), true);
        assertEquals(amount, 1);

        final int amount2 = logRepository.amountOfInstanceMonitorLogs(applicationInstance.guid(), false);
        assertEquals(amount2, 3);

    }

    @Test
    public void findFrequencyMonitorLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(36);
        logRepository.saveOrUpdate(monitorLog2);

        fullClean();

        final List<FrequencyMonitorLog> list = logRepository.findFrequencyMonitorLogs(applicationInstance.guid(), "2014-02-04 12:23:32");
        assertEquals(list.size(), 2);
    }

    @Test
    public void findLatestFrequencyMonitorLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        final List<FrequencyMonitorLog> list = logRepository.findLatestFrequencyMonitorLogs(applicationInstance, 23);
        assertEquals(list.size(), 1);
    }

    @Test
    public void findHadLogInstances() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        final List<ApplicationInstance> list = logRepository.findHadLogInstances();
        assertEquals(list.size(), 1);

    }

    @Test
    public void findHadLogInstancesByEnabled() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        final List<ApplicationInstance> list = logRepository.findHadLogInstancesByEnabled(false);
        assertEquals(list.size(), 1);

    }

    @Test
    public void findFrequencyMonitorLogList() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("instanceGuid", applicationInstance.guid());
        map.put("normal", 0);
        map.put("orderItem", "createTime");


        final List<FrequencyMonitorLog> list = logRepository.findFrequencyMonitorLogList(map);
        assertEquals(list.size(), 1);

        final int i = logRepository.totalFrequencyMonitorLogList(map);
        assertEquals(i, 1);
    }

    @Test
    public void findMonitoringReminderLogList() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        MonitoringReminderLog reminderLog = new MonitoringReminderLog()
                .monitorLog(monitorLog).instance(applicationInstance)
                .changeNormal(true);
        logRepository.saveOrUpdate(reminderLog);
        MonitoringReminderLog reminderLog2 = new MonitoringReminderLog()
                .monitorLog(monitorLog).instance(applicationInstance)
                .changeNormal(false);
        logRepository.saveOrUpdate(reminderLog2);

        fullClean();

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("instanceGuid", applicationInstance.guid());
        map.put("normal", 1);


        final List<MonitoringReminderLog> list = logRepository.findMonitoringReminderLogList(map);
        assertEquals(list.size(), 1);

        final int i = logRepository.totalMonitoringReminderLogList(map);
        assertEquals(i, 1);
    }

    @Test
    public void findHBSearchMonitorLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(true)
                .costTime(23);
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("key", "nd");

        final List<FrequencyMonitorLog> list = logRepository.findHBSearchMonitorLogs(map);
        assertEquals(list.size(), 1);

        final int i = logRepository.totalHBSearchMonitorLogs(map);
        assertEquals(i, 1);
    }

}
