package com.andaily.service;

import com.andaily.domain.dto.log.FrequencyMonitorLogListDto;
import com.andaily.domain.dto.log.ReminderLogListDto;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */

public interface LogService {

    FrequencyMonitorLogListDto loadFrequencyMonitorLogListDto(FrequencyMonitorLogListDto listDto);

    ReminderLogListDto loadReminderLogListDto(ReminderLogListDto listDto);
}