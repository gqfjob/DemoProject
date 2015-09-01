package com.andaily.domain.dto;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.dto.application.ApplicationInstanceDto;
import com.andaily.domain.dto.log.FrequencyMonitorLogDto;
import com.andaily.domain.log.FrequencyMonitorLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 15-3-13
 *
 * @author Shengzhao Li
 */
public class HBSearchResultDto extends AbstractDto {

    private ApplicationInstanceDto instanceDto;

    private FrequencyMonitorLogDto monitorLogDto;

    public HBSearchResultDto() {
    }

    public HBSearchResultDto(ApplicationInstance instance) {
        super(instance.guid());
        this.instanceDto = new ApplicationInstanceDto(instance);
    }

    public HBSearchResultDto(FrequencyMonitorLog monitorLog) {
        super(monitorLog.guid());
        this.monitorLogDto = new FrequencyMonitorLogDto(monitorLog);
    }

    public FrequencyMonitorLogDto getMonitorLogDto() {
        return monitorLogDto;
    }

    public void setMonitorLogDto(FrequencyMonitorLogDto monitorLogDto) {
        this.monitorLogDto = monitorLogDto;
    }

    public ApplicationInstanceDto getInstanceDto() {
        return instanceDto;
    }

    public void setInstanceDto(ApplicationInstanceDto instanceDto) {
        this.instanceDto = instanceDto;
    }

    public static List<HBSearchResultDto> toInstanceDtos(List<ApplicationInstance> instances) {
        List<HBSearchResultDto> dtos = new ArrayList<>(instances.size());
        for (ApplicationInstance instance : instances) {
            dtos.add(new HBSearchResultDto(instance));
        }
        return dtos;
    }

    public static List<HBSearchResultDto> toMonitorLogDtos(List<FrequencyMonitorLog> monitorLogs) {
        List<HBSearchResultDto> dtos = new ArrayList<>(monitorLogs.size());
        for (FrequencyMonitorLog monitorLog : monitorLogs) {
            dtos.add(new HBSearchResultDto(monitorLog));
        }
        return dtos;
    }
}
