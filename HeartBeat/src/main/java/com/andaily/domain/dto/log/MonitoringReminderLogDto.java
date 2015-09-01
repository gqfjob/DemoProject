package com.andaily.domain.dto.log;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.dto.AbstractDto;
import com.andaily.domain.log.MonitoringReminderLog;
import com.andaily.infrastructure.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 15-3-14
 *
 * @author Shengzhao Li
 */
public class MonitoringReminderLogDto extends AbstractDto {


    private String createTime;

    private FrequencyMonitorLogDto monitorLogDto;

    private String instanceGuid;
    private String instanceName;

    private boolean changeNormal;

    private String receiveEmail;

    private String emailContent;


    public MonitoringReminderLogDto() {
    }

    public MonitoringReminderLogDto(MonitoringReminderLog log) {
        super(log.guid());
        this.createTime = DateUtils.toDateTime(log.createTime());
        this.monitorLogDto = new FrequencyMonitorLogDto(log.monitorLog());

        final ApplicationInstance instance = log.instance();
        this.instanceGuid = instance.guid();
        this.instanceName = instance.instanceName();

        this.changeNormal = log.changeNormal();
        this.emailContent = log.emailContent();
        this.receiveEmail = log.receiveEmail();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public FrequencyMonitorLogDto getMonitorLogDto() {
        return monitorLogDto;
    }

    public void setMonitorLogDto(FrequencyMonitorLogDto monitorLogDto) {
        this.monitorLogDto = monitorLogDto;
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

    public boolean isChangeNormal() {
        return changeNormal;
    }

    public void setChangeNormal(boolean changeNormal) {
        this.changeNormal = changeNormal;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public static List<MonitoringReminderLogDto> toDtosa(List<MonitoringReminderLog> logs) {
        List<MonitoringReminderLogDto> dtos = new ArrayList<>(logs.size());
        for (MonitoringReminderLog log : logs) {
            dtos.add(new MonitoringReminderLogDto(log));
        }
        return dtos;
    }
}
