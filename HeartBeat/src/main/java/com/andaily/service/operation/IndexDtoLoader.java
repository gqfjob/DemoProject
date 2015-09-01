package com.andaily.service.operation;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.dto.IndexDto;
import com.andaily.domain.dto.IndexInstanceDto;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.infrastructure.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class IndexDtoLoader {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private IndexDto indexDto;

    public IndexDtoLoader(IndexDto indexDto) {
        this.indexDto = indexDto;
    }

    public IndexDto load() {
        final List<IndexInstanceDto> instanceDtos = indexDto.getInstanceDtos();
        final List<ApplicationInstance> instances = loadInstances();
        for (ApplicationInstance instance : instances) {
            instanceDtos.add(generateIndexInstanceDto(instance));
        }

        return indexDto;
    }

    private List<ApplicationInstance> loadInstances() {
        final boolean enabled = indexDto.isEnabled();
        return enabled ? logRepository.findHadLogInstancesByEnabled(enabled) : logRepository.findHadLogInstances();
    }

    private IndexInstanceDto generateIndexInstanceDto(ApplicationInstance instance) {
        IndexInstanceDto indexInstanceDto = new IndexInstanceDto(instance);
        List<FrequencyMonitorLog> monitorLogs = logRepository.findLatestFrequencyMonitorLogs(instance, indexDto.getMaxResult());

        MonitoringChartDataGenerator chartDataGenerator = new MonitoringChartDataGenerator(monitorLogs);
        indexInstanceDto.setCategoryData(chartDataGenerator.generateCategoryData());
        indexInstanceDto.setSeriesData(chartDataGenerator.generateSeriesData());

        lastLogDate(indexInstanceDto, monitorLogs);
        return indexInstanceDto;
    }

    private void lastLogDate(IndexInstanceDto indexInstanceDto, List<FrequencyMonitorLog> monitorLogs) {
        final Date time = monitorLogs.isEmpty() ? DateUtils.now() : monitorLogs.get(0).createTime();
        indexInstanceDto.setLastLogDate(DateUtils.toDateText(time, DateUtils.DEFAULT_DATE_TIME_FORMAT));
    }
}