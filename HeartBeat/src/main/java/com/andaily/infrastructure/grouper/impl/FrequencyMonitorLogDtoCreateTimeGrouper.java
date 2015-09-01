package com.andaily.infrastructure.grouper.impl;

import com.andaily.domain.dto.log.FrequencyMonitorLogDto;
import com.andaily.infrastructure.grouper.AbstractGrouper;

import java.util.List;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogDtoCreateTimeGrouper extends AbstractGrouper<String, FrequencyMonitorLogDto> {


    public FrequencyMonitorLogDtoCreateTimeGrouper(List<FrequencyMonitorLogDto> elements) {
        super(elements);
    }

    @Override
    public String groupKey(FrequencyMonitorLogDto element) {
        return element.getCreateDate();
    }
}
