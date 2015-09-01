package com.andaily.service.operation.job;

import com.andaily.domain.shared.BeanProvider;
import com.andaily.service.ApplicationInstanceService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Job is concurrently
 *
 * @author Shengzhao Li
 */
@DisallowConcurrentExecution
public class MonitoringInstanceJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringInstanceJob.class);
    public static final String APPLICATION_INSTANCE_GUID = "instanceGuid";

    private transient ApplicationInstanceService instanceService = BeanProvider.getBean(ApplicationInstanceService.class);

    public MonitoringInstanceJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        final JobKey key = context.getJobDetail().getKey();
        LOGGER.debug("*****  Start execute Job [{}]", key);

        final String instanceGuid = context.getMergedJobDataMap().getString(APPLICATION_INSTANCE_GUID);
        instanceService.executePerHeartBeatByInstanceGuid(instanceGuid);

        LOGGER.debug("&&&&&  End execute Job [{}]", key);
    }
}