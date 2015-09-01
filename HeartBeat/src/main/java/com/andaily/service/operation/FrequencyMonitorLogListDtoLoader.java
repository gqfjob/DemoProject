package com.andaily.service.operation;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.dto.application.ApplicationInstanceDto;
import com.andaily.domain.dto.log.FrequencyMonitorLogDto;
import com.andaily.domain.dto.log.FrequencyMonitorLogListDto;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.shared.paginated.PaginatedLoader;

import java.util.List;
import java.util.Map;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogListDtoLoader {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);

    private FrequencyMonitorLogListDto listDto;

    public FrequencyMonitorLogListDtoLoader(FrequencyMonitorLogListDto listDto) {
        this.listDto = listDto;
    }

    public FrequencyMonitorLogListDto load() {

        loadInstanceDtos();

        final Map<String, Object> map = listDto.queryMap();
        return listDto.load(new PaginatedLoader<FrequencyMonitorLogDto>() {
            @Override
            public List<FrequencyMonitorLogDto> loadList() {
                List<FrequencyMonitorLog> logs = logRepository.findFrequencyMonitorLogList(map);
                return FrequencyMonitorLogDto.toDtos(logs);
            }

            @Override
            public int loadTotalSize() {
                return logRepository.totalFrequencyMonitorLogList(map);
            }
        });
    }

    private void loadInstanceDtos() {
        List<ApplicationInstance> instances = logRepository.findHadLogInstances();
        List<ApplicationInstanceDto> instanceDtos = listDto.getInstanceDtos();
        for (ApplicationInstance instance : instances) {
            instanceDtos.add(new ApplicationInstanceDto(instance));
        }
    }
}
