package com.andaily.service.operation.instance;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.infrastructure.scheduler.DynamicJob;
import com.andaily.infrastructure.scheduler.DynamicSchedulerFactory;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shengzhao Li
 */
public class MonitoringApplicationInstanceKiller {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringApplicationInstanceKiller.class);
    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private String guid;

    public MonitoringApplicationInstanceKiller(String guid) {
        this.guid = guid;
    }

    /*
     * 1. Remove the job
     * 2. update instance to enabled=false
     *
     */
    public boolean kill() {
        final ApplicationInstance instance = instanceRepository.findByGuid(guid, ApplicationInstance.class);
        if (!instance.enabled()) {
            LOGGER.debug("<{}> Expect ApplicationInstance[guid={}] enabled=true,but it is false, illegal status",
                    SecurityUtils.currentUsername(), instance.guid());
            return false;
        }

        if (!pauseJob(instance)) {
            LOGGER.debug("<{}> Pause Job[name={}] failed", SecurityUtils.currentUsername(), instance.jobName());
            return false;
        }

        //update
        instance.enabled(false);
        LOGGER.debug("<{}> Update ApplicationInstance[guid={}] enabled=false", SecurityUtils.currentUsername(), instance.guid());
        return true;
    }

    private boolean pauseJob(ApplicationInstance instance) {
        DynamicJob job = new DynamicJob(instance.jobName());
        try {
            return DynamicSchedulerFactory.pauseJob(job);
        } catch (SchedulerException e) {
            LOGGER.error("<{}> Pause [" + job + "] failed", SecurityUtils.currentUsername(), e);
            return false;
        }
    }
}