package com.andaily.service.impl;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.dto.application.ApplicationInstanceDto;
import com.andaily.domain.dto.application.ApplicationInstanceFormDto;
import com.andaily.domain.dto.application.ApplicationInstanceListDto;
import com.andaily.domain.dto.application.InstanceStatisticsDto;
import com.andaily.domain.shared.paginated.PaginatedLoader;
import com.andaily.service.ApplicationInstanceService;
import com.andaily.service.operation.instance.*;
import com.andaily.service.operation.job.PerHeartBeatExecutor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
@Service("applicationInstanceService")
public class ApplicationInstanceServiceImpl implements ApplicationInstanceService {

    @Autowired
    private ApplicationInstanceRepository instanceRepository;

    @Override
    public void loadApplicationInstanceListDto(ApplicationInstanceListDto listDto) {
        final Map<String, Object> map = listDto.queryMap();
        listDto.load(new PaginatedLoader<ApplicationInstanceDto>() {
            @Override
            public List<ApplicationInstanceDto> loadList() {
                List<ApplicationInstance> instances = instanceRepository.findApplicationInstanceList(map);
                return ApplicationInstanceDto.toDtos(instances);
            }

            @Override
            public int loadTotalSize() {
                return instanceRepository.totalApplicationInstanceList(map);
            }
        });
    }

    @Override
    public ApplicationInstanceFormDto loadApplicationInstanceFormDto(String guid) {
        if (StringUtils.isNotEmpty(guid)) {
            ApplicationInstance instance = instanceRepository.findByGuid(guid, ApplicationInstance.class);
            if (instance.enabled()) {
                throw new IllegalStateException("Only Disabled ApplicationInstance support edit");
            }
            return new ApplicationInstanceFormDto(instance);
        }
        return new ApplicationInstanceFormDto();
    }

    @Override
    public void persistApplicationInstance(ApplicationInstanceFormDto formDto) {
        ApplicationInstanceFormDtoPersister persister = new ApplicationInstanceFormDtoPersister(formDto);
        persister.persist();
    }

    @Override
    public boolean enableApplicationInstance(String guid) {
        ApplicationInstanceEnabler instanceEnabler = new ApplicationInstanceEnabler(guid);
        return instanceEnabler.enable();
    }

    @Override
    public void executePerHeartBeatByInstanceGuid(String instanceGuid) {
        PerHeartBeatExecutor perHeartBeatExecutor = new PerHeartBeatExecutor(instanceGuid);
        perHeartBeatExecutor.execute();
    }

    @Override
    public boolean stopMonitoringApplicationInstance(String guid) {
        MonitoringApplicationInstanceKiller instanceKiller = new MonitoringApplicationInstanceKiller(guid);
        return instanceKiller.kill();
    }

    @Override
    public boolean deleteApplicationInstance(String guid) {
        ApplicationInstanceDeleter instanceDeleter = new ApplicationInstanceDeleter(guid);
        return instanceDeleter.delete();
    }

    @Override
    public InstanceStatisticsDto loadInstanceStatisticsDto(String guid) {
        InstanceStatisticsDtoLoader statisticsDtoLoader = new InstanceStatisticsDtoLoader(guid);
        return statisticsDtoLoader.load();
    }
}
