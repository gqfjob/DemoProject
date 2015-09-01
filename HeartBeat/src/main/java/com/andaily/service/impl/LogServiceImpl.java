package com.andaily.service.impl;

import com.andaily.domain.dto.log.FrequencyMonitorLogListDto;
import com.andaily.domain.dto.log.ReminderLogListDto;
import com.andaily.service.LogService;
import com.andaily.service.operation.FrequencyMonitorLogListDtoLoader;
import com.andaily.service.operation.ReminderLogListDtoLoader;
import org.springframework.stereotype.Service;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
@Service("logService")
public class LogServiceImpl implements LogService {


    @Override
    public FrequencyMonitorLogListDto loadFrequencyMonitorLogListDto(FrequencyMonitorLogListDto listDto) {
        FrequencyMonitorLogListDtoLoader dtoLoader = new FrequencyMonitorLogListDtoLoader(listDto);
        return dtoLoader.load();
    }

    @Override
    public ReminderLogListDto loadReminderLogListDto(ReminderLogListDto listDto) {
        ReminderLogListDtoLoader dtoLoader = new ReminderLogListDtoLoader(listDto);
        return dtoLoader.load();
    }
}
