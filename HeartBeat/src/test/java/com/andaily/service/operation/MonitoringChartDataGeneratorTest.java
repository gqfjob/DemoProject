package com.andaily.service.operation;

import com.andaily.domain.log.FrequencyMonitorLog;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * @author Shengzhao Li
 */
public class MonitoringChartDataGeneratorTest {


    @Test
    public void testGenerate() throws Exception {
        List<FrequencyMonitorLog> monitorLogs = new ArrayList<>();

        MonitoringChartDataGenerator generator = new MonitoringChartDataGenerator(monitorLogs);
        final String s = generator.generateCategoryData();
        assertEquals(s, "[]");
        assertEquals(generator.generateSeriesData(), "[]");


        monitorLogs.add(new FrequencyMonitorLog().normal(true).costTime(120));
        monitorLogs.add(new FrequencyMonitorLog().normal(true).costTime(100));
        monitorLogs.add(new FrequencyMonitorLog().normal(false).costTime(198));


        generator = new MonitoringChartDataGenerator(monitorLogs);
        assertNotEquals(generator.generateCategoryData(), "[]");
        assertNotEquals(generator.generateSeriesData(), "[]");

    }

    @Test
    public void generateAdditionData() throws Exception {
        List<FrequencyMonitorLog> monitorLogs = new ArrayList<>();

        MonitoringChartDataGenerator generator = new MonitoringChartDataGenerator(monitorLogs);
        final String s = generator.generateAdditionData();
        assertEquals(s, "[]");


        monitorLogs.add(new FrequencyMonitorLog().normal(true).costTime(120));
        monitorLogs.add(new FrequencyMonitorLog().normal(true).costTime(100));
        monitorLogs.add(new FrequencyMonitorLog().normal(false).costTime(198));


        generator = new MonitoringChartDataGenerator(monitorLogs);
        assertNotEquals(generator.generateAdditionData(), "[]");

    }
}