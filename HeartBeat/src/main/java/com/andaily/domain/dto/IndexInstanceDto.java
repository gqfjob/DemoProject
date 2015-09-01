package com.andaily.domain.dto;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.dto.application.ApplicationInstanceDto;

/**
 * @author Shengzhao Li
 */
public class IndexInstanceDto extends ApplicationInstanceDto {


    private String categoryData;
    private String seriesData;

    //yyyy-mm-dd HH:mm:ss
    private String lastLogDate;

    private int intervalTime;

    public IndexInstanceDto() {
    }

    public IndexInstanceDto(ApplicationInstance instance) {
        super(instance);
        this.intervalTime = instance.frequency().getMilliSeconds();
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getLastLogDate() {
        return lastLogDate;
    }

    public void setLastLogDate(String lastLogDate) {
        this.lastLogDate = lastLogDate;
    }

    public String getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(String categoryData) {
        this.categoryData = categoryData;
    }

    public String getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(String seriesData) {
        this.seriesData = seriesData;
    }
}