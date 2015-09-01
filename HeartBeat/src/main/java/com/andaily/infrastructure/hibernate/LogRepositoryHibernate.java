package com.andaily.infrastructure.hibernate;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.log.MonitoringReminderLog;
import com.andaily.infrastructure.DateUtils;
import com.andaily.infrastructure.hibernate.queryhelper.impl.FrequencyMonitorLogListQueryHelper;
import com.andaily.infrastructure.hibernate.queryhelper.impl.HBSearchMonitorLogsQueryHelper;
import com.andaily.infrastructure.hibernate.queryhelper.impl.ReminderLogListQueryHelper;
import com.google.common.collect.ImmutableMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
@Repository("logRepository")
public class LogRepositoryHibernate extends AbstractRepositoryHibernate<FrequencyMonitorLog> implements LogRepository {


    @Override
    public List<FrequencyMonitorLog> findFrequencyMonitorLogList(Map<String, Object> map) {
        FrequencyMonitorLogListQueryHelper queryHelper = new FrequencyMonitorLogListQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalFrequencyMonitorLogList(Map<String, Object> map) {
        FrequencyMonitorLogListQueryHelper queryHelper = new FrequencyMonitorLogListQueryHelper(session(), map);
        return queryHelper.getAmount();
    }

    @Override
    public List<ApplicationInstance> findHadLogInstances() {
        String hql = "select distinct ml.instance from FrequencyMonitorLog ml where ml.archived = 0 and ml.instance.archived = false ";
        return find(hql);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FrequencyMonitorLog> findLatestFrequencyMonitorLogs(ApplicationInstance instance, int maxResult) {
        String hql = " from FrequencyMonitorLog ml where ml.archived = false and ml.instance = :instance order by ml.createTime desc";
        final Query query = session().createQuery(hql)
                .setParameter("instance", instance)
                .setMaxResults(maxResult);
        return query.list();
    }

    @Override
    public List<FrequencyMonitorLog> findFrequencyMonitorLogs(String instanceGuid, String minCreateTime) {
        String hql = " from FrequencyMonitorLog ml where ml.archived = false and ml.instance.guid = :instanceGuid and ml.createTime > :minCreateTime order by ml.createTime desc";
        return find(hql, ImmutableMap.of("instanceGuid", instanceGuid, "minCreateTime", DateUtils.getDate(minCreateTime, DateUtils.DEFAULT_DATE_TIME_FORMAT)));
    }

    @Override
    public List<ApplicationInstance> findHadLogInstancesByEnabled(boolean enabled) {
        String hql = "select distinct ml.instance from FrequencyMonitorLog ml where ml.archived = 0 and ml.instance.enabled = :enabled " +
                "  and ml.instance.archived = false ";
        return find(hql, ImmutableMap.of("enabled", enabled));
    }

    @Override
    public FrequencyMonitorLog findLastLogByCurrentLog(FrequencyMonitorLog currentLog) {
        String hql = " from FrequencyMonitorLog ml where ml.archived = false and ml.instance = :instance and ml.createTime < :createTime order by ml.createTime desc ";
        final Query query = session().createQuery(hql)
                .setParameter("instance", currentLog.instance())
                .setParameter("createTime", currentLog.createTime())
                .setMaxResults(1);
        return (FrequencyMonitorLog) query.uniqueResult();
    }

    @Override
    public FrequencyMonitorLog findLastNotNormalFrequencyMonitorLog(String instanceGuid) {
        String hql = "  from FrequencyMonitorLog ml where ml.archived = false and ml.instance.guid = :instanceGuid and ml.normal = :normal " +
                " and ml.createTime < :createTime order by ml.createTime desc  ";
        final Query query = session().createQuery(hql)
                .setParameter("instanceGuid", instanceGuid)
                .setParameter("createTime", DateUtils.now())
                .setParameter("normal", false)
                .setMaxResults(1);
        return (FrequencyMonitorLog) query.uniqueResult();
    }

    @Override
    public int amountOfInstanceMonitorLogs(String instanceGuid, boolean normal) {
        String hql = " select count(ml.guid) from FrequencyMonitorLog ml where ml.archived = false and ml.instance.guid = :instanceGuid and ml.normal = :normal ";
        final Query query = session().createQuery(hql)
                .setParameter("instanceGuid", instanceGuid)
                .setParameter("normal", normal)
                .setMaxResults(1);

        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public int totalMonitoringReminderLogs(String instanceGuid) {
        String hql = " select count(ml.guid) from  MonitoringReminderLog ml where archived = false and ml.instance.guid = :instanceGuid ";
        final Query query = session().createQuery(hql)
                .setParameter("instanceGuid", instanceGuid)
                .setMaxResults(1);

        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public int amountOfMonitoringReminderLogs(String instanceGuid, boolean changeNormal) {
        String hql = " select count(ml.guid) from  MonitoringReminderLog ml where archived = false and ml.instance.guid = :instanceGuid and ml.changeNormal = :changeNormal ";
        final Query query = session().createQuery(hql)
                .setParameter("instanceGuid", instanceGuid)
                .setParameter("changeNormal", changeNormal)
                .setMaxResults(1);

        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Date findFirstMonitoringLogTime(String instanceGuid, boolean normal) {
        String hql = " select ml.createTime from  FrequencyMonitorLog ml where archived = false " +
                " and ml.instance.guid = :instanceGuid and ml.normal = :normal order by ml.createTime asc ";
        final Query query = session().createQuery(hql)
                .setParameter("instanceGuid", instanceGuid)
                .setParameter("normal", normal)
                .setMaxResults(1);

        return (Date) query.uniqueResult();
    }

    @Override
    public Date findFirstMonitoringLogTimeAfterSpecifyTime(String instanceGuid, Date specifyTime) {
        String hql = " select ml.createTime from  FrequencyMonitorLog ml where archived = false " +
                " and ml.instance.guid = :instanceGuid and ml.createTime > :time order by ml.createTime asc ";
        final Query query = session().createQuery(hql)
                .setParameter("instanceGuid", instanceGuid)
                .setParameter("time", specifyTime)
                .setMaxResults(1);

        return (Date) query.uniqueResult();
    }

    @Override
    public List<FrequencyMonitorLog> findHBSearchMonitorLogs(Map<String, Object> map) {
        HBSearchMonitorLogsQueryHelper queryHelper = new HBSearchMonitorLogsQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalHBSearchMonitorLogs(Map<String, Object> map) {
        HBSearchMonitorLogsQueryHelper queryHelper = new HBSearchMonitorLogsQueryHelper(session(), map);
        return queryHelper.getAmount();
    }

    @Override
    public List<MonitoringReminderLog> findMonitoringReminderLogList(Map<String, Object> map) {
        ReminderLogListQueryHelper queryHelper = new ReminderLogListQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalMonitoringReminderLogList(Map<String, Object> map) {
        ReminderLogListQueryHelper queryHelper = new ReminderLogListQueryHelper(session(), map);
        return queryHelper.getAmount();
    }

    @Override
    public List<ApplicationInstance> findDistinctReminderLogInstances() {
        String hql = " select distinct rl.instance from MonitoringReminderLog rl where rl.archived = false ";
        return find(hql);
    }
}