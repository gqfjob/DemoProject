package com.andaily.service.operation;

import com.andaily.domain.dto.IndexAdditionInstanceDto;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.infrastructure.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class IndexAdditionInstanceDtoLoader {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private IndexAdditionInstanceDto additionInstanceDto;

    public IndexAdditionInstanceDtoLoader(IndexAdditionInstanceDto additionInstanceDto) {
        this.additionInstanceDto = additionInstanceDto;
    }

    public IndexAdditionInstanceDto load() {

        List<FrequencyMonitorLog> monitorLogs = logRepository.findFrequencyMonitorLogs(additionInstanceDto.getGuid(), additionInstanceDto.getLastLogDate());
        MonitoringChartDataGenerator chartDataGenerator = new MonitoringChartDataGenerator(monitorLogs);
        additionInstanceDto.setAdditionData(chartDataGenerator.generateAdditionData());

        updateLastLogDate(monitorLogs);
        return additionInstanceDto;
    }

    private void updateLastLogDate(List<FrequencyMonitorLog> monitorLogs) {
        if (monitorLogs.isEmpty()) {
            return;
        }
        final Date time = monitorLogs.get(0).createTime();
        additionInstanceDto.setLastLogDate(DateUtils.toDateText(time, DateUtils.DEFAULT_DATE_TIME_FORMAT));
    }
}