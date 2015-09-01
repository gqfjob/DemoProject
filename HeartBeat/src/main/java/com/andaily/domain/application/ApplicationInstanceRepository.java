package com.andaily.domain.application;

import com.andaily.domain.shared.Repository;

import java.util.List;
import java.util.Map;

/**
 * 15-1-2
 *
 * @author Shengzhao Li
 */

public interface ApplicationInstanceRepository extends Repository {

    List<ApplicationInstance> findApplicationInstanceList(Map<String, Object> map);

    int totalApplicationInstanceList(Map<String, Object> map);

    int deleteInstanceFrequencyMonitorLogs(String instanceGuid);

    List<ApplicationInstance> findAllEnabledInstances();

    List<ApplicationInstance> findHBSearchInstances(Map<String, Object> map);

    int totalHBSearchInstances(Map<String, Object> map);

    int deleteInstanceMonitoringReminderLogs(String instanceGuid);
}