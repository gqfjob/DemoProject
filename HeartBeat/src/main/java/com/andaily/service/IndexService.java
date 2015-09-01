package com.andaily.service;

import com.andaily.domain.dto.HBSearchDto;
import com.andaily.domain.dto.IndexAdditionInstanceDto;
import com.andaily.domain.dto.IndexDto;
import com.andaily.domain.dto.MonitoringInstanceDto;

/**
 * @author Shengzhao Li
 */

public interface IndexService {

    IndexDto loadIndexDto(IndexDto indexDto);

    IndexAdditionInstanceDto loadIndexAdditionInstanceDto(IndexAdditionInstanceDto additionInstanceDto);

    MonitoringInstanceDto loadMonitoringInstanceDto(String guid);

    HBSearchDto loadHBSearchDto(HBSearchDto searchDto);

    boolean loadAllowUserRegister();
}