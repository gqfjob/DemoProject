package com.andaily.infrastructure.hibernate;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.application.ApplicationInstanceURL;
import com.andaily.domain.application.InstanceMonitorURLParameter;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.MonitoringReminderLog;
import com.andaily.domain.shared.GuidGenerator;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.infrastructure.AbstractRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * 15-1-2
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceRepositoryHibernateTest extends AbstractRepositoryTest {

    @Autowired
    private ApplicationInstanceRepository repository;


    @Test
    public void testInstanceMonitorURLParameter() {
        ApplicationInstance instance = new ApplicationInstance()
                .instanceName("Andaily");

        final InstanceMonitorURLParameter parameter = new InstanceMonitorURLParameter()
                .key("guid").value(GuidGenerator.generate()).instance(instance);
        instance.addMonitorURLParameter(parameter);

        final InstanceMonitorURLParameter parameter2 = new InstanceMonitorURLParameter()
                .key("name").value("odedd").instance(instance);
        instance.addMonitorURLParameter(parameter2);

        repository.saveOrUpdate(instance);

        fullClean();

        final ApplicationInstance instance1 = repository.findByGuid(instance.guid(), ApplicationInstance.class);
        assertNotNull(instance1);
        final ApplicationInstanceURL url = instance1.instanceURL();
        assertNotNull(url);

        final List<InstanceMonitorURLParameter> parameters = url.urlParameters();
        assertEquals(parameters.size(), 2);

        final InstanceMonitorURLParameter parameter1 = parameters.get(0);
        assertNotNull(parameter1);
        assertNotNull(parameter1.key());
        assertNotNull(parameter1.value());
        assertNotNull(parameter1.instance());

    }


    @Test
    public void findApplicationInstanceList() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("instanceName", "A");

        List<ApplicationInstance> list = repository.findApplicationInstanceList(map);
        assertEquals(list.size(), 1);

        int i = repository.totalApplicationInstanceList(map);
        assertEquals(i, 1);
    }

    @Test
    public void findHBSearchInstances() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("key", "A");

        fullClean();

        final List<ApplicationInstance> list = repository.findHBSearchInstances(map);
        assertEquals(list.size(), 1);

        final int i = repository.totalHBSearchInstances(map);
        assertEquals(i, 1);
    }

    @Test
    public void findAllEnabledInstances() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        fullClean();

        List<ApplicationInstance> list = repository.findAllEnabledInstances();
        assertTrue(list.size() == 0);
    }


    @Test
    public void deleteInstanceFrequencyMonitorLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance);
        repository.saveOrUpdate(monitorLog);

        fullClean();

        final int count = repository.deleteInstanceFrequencyMonitorLogs(applicationInstance.guid());
        assertEquals(count, 1);


    }

    @Test
    public void deleteInstanceMonitoringReminderLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance);
        repository.saveOrUpdate(monitorLog);

        MonitoringReminderLog reminderLog = new MonitoringReminderLog()
                .instance(applicationInstance);
        repository.saveOrUpdate(reminderLog);

        fullClean();

        final int count = repository.deleteInstanceMonitoringReminderLogs(applicationInstance.guid());
        assertEquals(count, 1);


    }

    @Test
    public void testApplicationInstance() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        applicationInstance.instanceURL().contentType("text/xml");

        repository.saveOrUpdate(applicationInstance);

        fullClean();

        ApplicationInstance instance = repository.findByGuid(applicationInstance.guid(), ApplicationInstance.class);
        assertNotNull(instance);
        assertNotNull(instance.requestMethod());
        assertNotNull(instance.instanceName());

        assertNotNull(instance.instanceURL().contentType());

    }

}
