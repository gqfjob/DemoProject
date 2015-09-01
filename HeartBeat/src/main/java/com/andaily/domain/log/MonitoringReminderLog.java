package com.andaily.domain.log;

import com.andaily.domain.AbstractDomain;
import com.andaily.domain.application.ApplicationInstance;

import javax.persistence.*;

/**
 * 每次监控若有发送提醒(reminder),则记录一条日志
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "monitoring_reminder_log")
public class MonitoringReminderLog extends AbstractDomain {


    @ManyToOne
    @JoinColumn(name = "monitor_log_id")
    private FrequencyMonitorLog monitorLog;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private ApplicationInstance instance;

    //是否为监控由不正常 -> 正常的提醒日志
    @Column(name = "change_normal")
    private boolean changeNormal;


    //提醒接收的 邮件地址
    @Column(name = "receive_email")
    private String receiveEmail;

    //HTML format
    @Column(name = "email_content")
    private String emailContent;

    /*
   * Default constructor
   * */
    public MonitoringReminderLog() {
    }

    public String emailContent() {
        return emailContent;
    }

    public MonitoringReminderLog emailContent(String emailContent) {
        this.emailContent = emailContent;
        return this;
    }

    public MonitoringReminderLog(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
        this.instance = monitorLog.instance();
    }

    public ApplicationInstance instance() {
        return instance;
    }

    public MonitoringReminderLog instance(ApplicationInstance instance) {
        this.instance = instance;
        return this;
    }

    public FrequencyMonitorLog monitorLog() {
        return monitorLog;
    }

    public MonitoringReminderLog monitorLog(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
        return this;
    }

    public boolean changeNormal() {
        return changeNormal;
    }

    public MonitoringReminderLog changeNormal(boolean changeNormal) {
        this.changeNormal = changeNormal;
        return this;
    }

    public String receiveEmail() {
        return receiveEmail;
    }

    public MonitoringReminderLog receiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
        return this;
    }
}