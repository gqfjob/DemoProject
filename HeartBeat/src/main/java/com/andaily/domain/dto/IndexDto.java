package com.andaily.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class IndexDto extends AbstractDto {

    //default show latest 20  times data
    private int maxResult = 20;

    private boolean enabled = true;

    private List<IndexInstanceDto> instanceDtos = new ArrayList<>();


    public IndexDto() {
    }

    public List<IndexInstanceDto> getInstanceDtos() {
        return instanceDtos;
    }

    public boolean isEmployInstances() {
        return instanceDtos.isEmpty();
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}