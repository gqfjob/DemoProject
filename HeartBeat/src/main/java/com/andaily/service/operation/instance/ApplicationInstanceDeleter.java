package com.andaily.service.operation.instance;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.shared.BeanProvider;

import static com.andaily.domain.shared.security.SecurityUtils.currentUsername;

import com.andaily.infrastructure.scheduler.DynamicJob;
import com.andaily.infrastructure.scheduler.DynamicSchedulerFactory;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shengzhao Li
 */
public class ApplicationInstanceDeleter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInstanceDeleter.class);
    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private String guid;

    public ApplicationInstanceDeleter(String guid) {
        this.guid = guid;
    }

    public boolean delete() {
        final ApplicationInstance instance = instanceRepository.findByGuid(guid, ApplicationInstance.class);
        if (instance.enabled()) {
            LOGGER.debug("<{}> Forbid delete enabled ApplicationInstance[guid={}]", currentUsername(), instance.guid());
            return false;
        }

        instanceRepository.deleteInstanceFrequencyMonitorLogs(guid);
        instanceRepository.deleteInstanceMonitoringReminderLogs(guid);

        checkAndRemoveJob(instance);

        //logic delete
        instance.archived(true);
        LOGGER.debug("<{}> Archive ApplicationInstance[guid={}] and FrequencyMonitorLogs,MonitoringReminderLogs", currentUsername(), instance.guid());
        return true;
    }

    private void checkAndRemoveJob(ApplicationInstance instance) {
        DynamicJob job = new DynamicJob(instance.jobName());
        try {
            if (DynamicSchedulerFactory.existJob(job)) {
                final boolean result = DynamicSchedulerFactory.removeJob(job);
                LOGGER.debug("<{}> Remove DynamicJob[{}] result [{}]", currentUsername(), job, result);
            }
        } catch (SchedulerException e) {
            LOGGER.error("<{}> Remove [" + job + "] failed", currentUsername(), e);
        }
    }
}