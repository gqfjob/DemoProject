package com.andaily.service.operation.instance;

import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.dto.application.InstanceStatisticsDto;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.infrastructure.DateUtils;

import java.util.Date;

/**
 * @author Shengzhao Li
 */
public class InstanceStatisticsDtoLoader {


    private static final String EMPTY_TEXT = "---";

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private String guid;

    public InstanceStatisticsDtoLoader(String guid) {
        this.guid = guid;
    }

    public InstanceStatisticsDto load() {
        InstanceStatisticsDto statisticsDto = new InstanceStatisticsDto(guid);

        final Date lastNotNormalTime = lastNotNormalTime(statisticsDto);
        normalConnection(statisticsDto, lastNotNormalTime);

        normalAmount(statisticsDto);
        notNormalAmount(statisticsDto);

        interruptTime(statisticsDto);
        sendReminderTime(statisticsDto);

        return statisticsDto;
    }

    private void sendReminderTime(InstanceStatisticsDto statisticsDto) {
        int amount = logRepository.totalMonitoringReminderLogs(guid);
        statisticsDto.setSendReminderTime(amount / 2);
    }

    private void interruptTime(InstanceStatisticsDto statisticsDto) {
        int times = logRepository.amountOfMonitoringReminderLogs(guid, false);
        statisticsDto.setInterruptTime(times);
    }

    // Format:  0y 2M 1d 12h 23m 23s
    private void normalConnection(InstanceStatisticsDto statisticsDto, Date lastNotNormalTime) {
        Date firstNormalAfterLastNotNormalTime;
        if (lastNotNormalTime != null) {
            firstNormalAfterLastNotNormalTime = logRepository.findFirstMonitoringLogTimeAfterSpecifyTime(guid, lastNotNormalTime);
        } else {
            firstNormalAfterLastNotNormalTime = logRepository.findFirstMonitoringLogTime(guid, true);
        }

        if (firstNormalAfterLastNotNormalTime == null) {
            statisticsDto.setNormalConnection(EMPTY_TEXT);
        } else {
            final long seconds = DateUtils.periodAsSeconds(firstNormalAfterLastNotNormalTime, DateUtils.now());
            statisticsDto.setNormalConnection(DateUtils.secondsToText(seconds));
        }
    }

    private void notNormalAmount(InstanceStatisticsDto statisticsDto) {
        int notNormalAmount = logRepository.amountOfInstanceMonitorLogs(guid, false);
        statisticsDto.setNotNormalAmount(notNormalAmount);
    }

    private void normalAmount(InstanceStatisticsDto statisticsDto) {
        int normalAmount = logRepository.amountOfInstanceMonitorLogs(guid, true);
        statisticsDto.setNormalAmount(normalAmount);
    }

    private Date lastNotNormalTime(InstanceStatisticsDto statisticsDto) {
        FrequencyMonitorLog lastNotNormalLog = logRepository.findLastNotNormalFrequencyMonitorLog(guid);
        Date lastNotNormalTime = null;
        if (lastNotNormalLog != null) {
            lastNotNormalTime = lastNotNormalLog.createTime();
            statisticsDto.setLastNotNormalTime(DateUtils.toDateText(lastNotNormalTime, DateUtils.DEFAULT_DATE_TIME_FORMAT));
        } else {
            statisticsDto.setLastNotNormalTime(EMPTY_TEXT);
        }
        return lastNotNormalTime;
    }
}