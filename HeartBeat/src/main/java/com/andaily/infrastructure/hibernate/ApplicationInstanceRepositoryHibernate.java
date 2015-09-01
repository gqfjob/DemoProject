package com.andaily.infrastructure.hibernate;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.infrastructure.hibernate.queryhelper.impl.ApplicationInstanceListQueryHelper;
import com.andaily.infrastructure.hibernate.queryhelper.impl.HBSearchInstancesQueryHelper;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
@Repository("applicationInstanceRepository")
public class ApplicationInstanceRepositoryHibernate extends AbstractRepositoryHibernate<ApplicationInstance> implements ApplicationInstanceRepository {


    @Override
    public List<ApplicationInstance> findApplicationInstanceList(Map<String, Object> map) {
        ApplicationInstanceListQueryHelper queryHelper = new ApplicationInstanceListQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalApplicationInstanceList(Map<String, Object> map) {
        ApplicationInstanceListQueryHelper queryHelper = new ApplicationInstanceListQueryHelper(session(), map);
        return queryHelper.getAmount();
    }


    @Override
    public int deleteInstanceFrequencyMonitorLogs(String instanceGuid) {
        final List<FrequencyMonitorLog> list = find("from FrequencyMonitorLog ml where ml.instance.guid = :instanceGuid", ImmutableMap.of("instanceGuid", instanceGuid));
        deleteAll(list);
        return list.size();
    }

    @Override
    public List<ApplicationInstance> findAllEnabledInstances() {
        String hql = " from ApplicationInstance ai where ai.archived = false and ai.enabled = true";
        return find(hql);
    }

    @Override
    public List<ApplicationInstance> findHBSearchInstances(Map<String, Object> map) {
        HBSearchInstancesQueryHelper queryHelper = new HBSearchInstancesQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalHBSearchInstances(Map<String, Object> map) {
        HBSearchInstancesQueryHelper queryHelper = new HBSearchInstancesQueryHelper(session(), map);
        return queryHelper.getAmount();
    }

    @Override
    public int deleteInstanceMonitoringReminderLogs(String instanceGuid) {
        final List<FrequencyMonitorLog> list = find("from MonitoringReminderLog ml where ml.instance.guid = :instanceGuid", ImmutableMap.of("instanceGuid", instanceGuid));
        deleteAll(list);
        return list.size();
    }
}