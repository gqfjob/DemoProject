package com.andaily.service.impl;

import com.andaily.domain.dto.HBSearchDto;
import com.andaily.domain.dto.IndexAdditionInstanceDto;
import com.andaily.domain.dto.IndexDto;
import com.andaily.domain.dto.MonitoringInstanceDto;
import com.andaily.domain.dto.user.UserProfileDto;
import com.andaily.domain.shared.Application;
import com.andaily.service.IndexService;
import com.andaily.service.operation.search.HBSearchDtoLoader;
import com.andaily.service.operation.IndexAdditionInstanceDtoLoader;
import com.andaily.service.operation.IndexDtoLoader;
import com.andaily.service.operation.MonitoringInstanceDtoLoader;
import org.springframework.stereotype.Service;

/**
 * @author Shengzhao Li
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {


    @Override
    public IndexDto loadIndexDto(IndexDto indexDto) {
        IndexDtoLoader dtoLoader = new IndexDtoLoader(indexDto);
        return dtoLoader.load();
    }

    @Override
    public IndexAdditionInstanceDto loadIndexAdditionInstanceDto(IndexAdditionInstanceDto additionInstanceDto) {
        IndexAdditionInstanceDtoLoader dtoLoader = new IndexAdditionInstanceDtoLoader(additionInstanceDto);
        return dtoLoader.load();
    }

    @Override
    public MonitoringInstanceDto loadMonitoringInstanceDto(String guid) {
        MonitoringInstanceDtoLoader dtoLoader = new MonitoringInstanceDtoLoader(guid);
        return dtoLoader.load();
    }

    @Override
    public HBSearchDto loadHBSearchDto(HBSearchDto searchDto) {
        HBSearchDtoLoader dtoLoader = new HBSearchDtoLoader(searchDto);
        return dtoLoader.load();
    }

    @Override
    public boolean loadAllowUserRegister() {
        return Application.systemSetting().allowUserRegister();
    }
}