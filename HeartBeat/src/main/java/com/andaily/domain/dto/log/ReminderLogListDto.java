package com.andaily.domain.dto.log;

import com.andaily.domain.dto.application.ApplicationInstanceDto;
import com.andaily.domain.shared.paginated.DefaultPaginated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 15-3-14
 *
 * @author Shengzhao Li
 */
public class ReminderLogListDto extends DefaultPaginated<MonitoringReminderLogDto> {


    private String instanceGuid;
    //1 is un-normal -> normal,0 is normal -> un-normal , otherwise all
    private int normal = -1;


    private ApplicationInstanceDto instanceDto;
    private List<ApplicationInstanceDto> instanceDtos = new ArrayList<>();


    public ReminderLogListDto() {
    }


    @Override
    public Map<String, Object> queryMap() {
        final Map<String, Object> map = super.queryMap();
        map.put("instanceGuid", instanceGuid);
        map.put("normal", normal);
        return map;
    }

    public ApplicationInstanceDto getInstanceDto() {
        return instanceDto;
    }

    public void setInstanceDto(ApplicationInstanceDto instanceDto) {
        this.instanceDto = instanceDto;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public List<ApplicationInstanceDto> getInstanceDtos() {
        return instanceDtos;
    }

    public void setInstanceDtos(List<ApplicationInstanceDto> instanceDtos) {
        this.instanceDtos = instanceDtos;
    }
}
