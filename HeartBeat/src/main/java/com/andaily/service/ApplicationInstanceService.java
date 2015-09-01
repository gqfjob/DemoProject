package com.andaily.service;

import com.andaily.domain.dto.application.ApplicationInstanceFormDto;
import com.andaily.domain.dto.application.ApplicationInstanceListDto;
import com.andaily.domain.dto.application.InstanceStatisticsDto;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */

public interface ApplicationInstanceService {

    void loadApplicationInstanceListDto(ApplicationInstanceListDto listDto);

    ApplicationInstanceFormDto loadApplicationInstanceFormDto(String guid);

    void persistApplicationInstance(ApplicationInstanceFormDto formDto);

    boolean enableApplicationInstance(String guid);

    void executePerHeartBeatByInstanceGuid(String instanceGuid);

    boolean stopMonitoringApplicationInstance(String guid);

    boolean deleteApplicationInstance(String guid);

    InstanceStatisticsDto loadInstanceStatisticsDto(String guid);
}