package com.andaily.service.operation.job;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.reminder.PerMonitoringReminder;
import com.andaily.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shengzhao Li
 */
public class PerHeartBeatExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerHeartBeatExecutor.class);

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private String instanceGuid;

    public PerHeartBeatExecutor(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    /*
    * Execute heart-beat
    *
    * 1.Send request and checking response
    * 2.Generate FrequencyMonitorLog
    * 3. If failed will notice
    * */
    public void execute() {
        final ApplicationInstance instance = instanceRepository.findByGuid(instanceGuid, ApplicationInstance.class);
        final FrequencyMonitorLog monitorLog = generateMonitorLog(instance);

        instanceRepository.saveOrUpdate(monitorLog);
        LOGGER.debug("Generate and persist FrequencyMonitorLog[{}]", monitorLog);
        //reminder
        remind(monitorLog);
    }

    private void remind(FrequencyMonitorLog monitorLog) {
        PerMonitoringReminder reminder = new PerMonitoringReminder(monitorLog);
        reminder.remind();
    }

    private FrequencyMonitorLog generateMonitorLog(ApplicationInstance instance) {
        FrequencyMonitorLogGenerator monitorLogGenerator = new FrequencyMonitorLogGenerator(instance);
        return monitorLogGenerator.generate();
    }
}