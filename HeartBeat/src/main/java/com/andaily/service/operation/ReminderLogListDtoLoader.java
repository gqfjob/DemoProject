package com.andaily.service.operation;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.dto.application.ApplicationInstanceDto;
import com.andaily.domain.dto.log.MonitoringReminderLogDto;
import com.andaily.domain.dto.log.ReminderLogListDto;
import com.andaily.domain.log.LogRepository;
import com.andaily.domain.log.MonitoringReminderLog;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.shared.paginated.PaginatedLoader;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 15-3-14
 *
 * @author Shengzhao Li
 */
public class ReminderLogListDtoLoader {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private ReminderLogListDto listDto;

    public ReminderLogListDtoLoader(ReminderLogListDto listDto) {
        this.listDto = listDto;
    }

    public ReminderLogListDto load() {
        loadInstanceDto();
        loadInstanceDtos();

        final Map<String, Object> map = listDto.queryMap();
        return listDto.load(new PaginatedLoader<MonitoringReminderLogDto>() {
            @Override
            public List<MonitoringReminderLogDto> loadList() {
                List<MonitoringReminderLog> reminderLogs = logRepository.findMonitoringReminderLogList(map);
                return MonitoringReminderLogDto.toDtosa(reminderLogs);
            }

            @Override
            public int loadTotalSize() {
                return logRepository.totalMonitoringReminderLogList(map);
            }
        });
    }

    private void loadInstanceDtos() {
        List<ApplicationInstance> instances = logRepository.findDistinctReminderLogInstances();
        listDto.setInstanceDtos(ApplicationInstanceDto.toDtos(instances));
    }

    private void loadInstanceDto() {
        final String instanceGuid = listDto.getInstanceGuid();
        if (StringUtils.isNotEmpty(instanceGuid)) {
            final ApplicationInstance instance = logRepository.findByGuid(instanceGuid, ApplicationInstance.class);
            listDto.setInstanceDto(new ApplicationInstanceDto(instance));
        }
    }
}
