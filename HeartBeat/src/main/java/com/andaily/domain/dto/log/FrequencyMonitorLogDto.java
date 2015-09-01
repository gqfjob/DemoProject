package com.andaily.domain.dto.log;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.dto.AbstractDto;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.infrastructure.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogDto extends AbstractDto {

    private static final BigDecimal KB = new BigDecimal("1024");

    private String instanceGuid;
    private String instanceName;

    private boolean normal;
    private String createTime;
    private String createDate;

    private long costTime;
    private String remark;

    private long responseSize;

    public FrequencyMonitorLogDto() {
    }

    public FrequencyMonitorLogDto(FrequencyMonitorLog log) {
        super(log.guid());
        final Date time = log.createTime();
        this.createTime = DateUtils.toDateText(time, DateUtils.DEFAULT_DATE_TIME_FORMAT);
        this.createDate = DateUtils.toDateText(time);

        ApplicationInstance instance = log.instance();
        this.instanceGuid = instance.guid();
        this.instanceName = instance.instanceName();

        this.normal = log.normal();
        this.costTime = log.costTime();
        this.remark = log.remark();

        this.responseSize = log.responseSize();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public BigDecimal getResponseSizeAsKB() {
        return new BigDecimal(responseSize).divide(KB, 2, BigDecimal.ROUND_HALF_UP);
    }

    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    public static List<FrequencyMonitorLogDto> toDtos(List<FrequencyMonitorLog> logs) {
        List<FrequencyMonitorLogDto> dtos = new ArrayList<>(logs.size());
        for (FrequencyMonitorLog log : logs) {
            dtos.add(new FrequencyMonitorLogDto(log));
        }
        return dtos;
    }
}
