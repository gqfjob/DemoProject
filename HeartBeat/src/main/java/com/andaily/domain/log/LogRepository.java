package com.andaily.domain.log;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.shared.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 15-1-2
 *
 * @author Shengzhao Li
 */

public interface LogRepository extends Repository {

    List<FrequencyMonitorLog> findFrequencyMonitorLogList(Map<String, Object> map);

    int totalFrequencyMonitorLogList(Map<String, Object> map);

    List<ApplicationInstance> findHadLogInstances();

    List<FrequencyMonitorLog> findLatestFrequencyMonitorLogs(ApplicationInstance instance, int maxResult);

    /**
     * Find  FrequencyMonitorLogs that larger than  minCreateTime and instance.guid =  instanceGuid
     *
     * @param instanceGuid  instanceGuid
     * @param minCreateTime minCreateTime
     * @return List of FrequencyMonitorLog
     */
    List<FrequencyMonitorLog> findFrequencyMonitorLogs(String instanceGuid, String minCreateTime);

    List<ApplicationInstance> findHadLogInstancesByEnabled(boolean enabled);

    FrequencyMonitorLog findLastLogByCurrentLog(FrequencyMonitorLog currentLog);

    FrequencyMonitorLog findLastNotNormalFrequencyMonitorLog(String instanceGuid);

    int amountOfInstanceMonitorLogs(String instanceGuid, boolean normal);

    int totalMonitoringReminderLogs(String instanceGuid);

    int amountOfMonitoringReminderLogs(String instanceGuid, boolean changeNormal);

    Date findFirstMonitoringLogTime(String instanceGuid, boolean normal);

    Date findFirstMonitoringLogTimeAfterSpecifyTime(String instanceGuid, Date specifyTime);

    List<FrequencyMonitorLog> findHBSearchMonitorLogs(Map<String, Object> map);

    int totalHBSearchMonitorLogs(Map<String, Object> map);

    List<MonitoringReminderLog> findMonitoringReminderLogList(Map<String, Object> map);

    int totalMonitoringReminderLogList(Map<String, Object> map);

    List<ApplicationInstance> findDistinctReminderLogInstances();
}