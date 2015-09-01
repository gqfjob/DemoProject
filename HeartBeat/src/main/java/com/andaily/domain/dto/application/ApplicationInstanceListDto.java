package com.andaily.domain.dto.application;

import com.andaily.domain.shared.paginated.DefaultPaginated;

import java.util.Map;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceListDto extends DefaultPaginated<ApplicationInstanceDto> {


    private String instanceName;

    public ApplicationInstanceListDto() {
    }

    @Override
    public Map<String, Object> queryMap() {
        Map<String, Object> map = super.queryMap();
        map.put("instanceName", instanceName);
        return map;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

}
