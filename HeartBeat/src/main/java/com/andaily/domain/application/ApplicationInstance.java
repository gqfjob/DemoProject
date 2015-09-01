package com.andaily.domain.application;

import com.andaily.domain.AbstractDomain;
import com.andaily.domain.user.User;

import javax.persistence.*;

/**
 * A server instance that is need monitoring
 * 定义一个需要心跳监测的应用实例
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "application_instance")
public class ApplicationInstance extends AbstractDomain {

    private static final long serialVersionUID = 1826152029135090793L;
    //实例名称
    @Column(name = "instance_name")
    private String instanceName;


    @Embedded
    private ApplicationInstanceURL instanceURL = new ApplicationInstanceURL();

    /**
     * 连接时超时的时间
     * 0,表示无超时
     */
    @Column(name = "max_connection_seconds")
    private int maxConnectionSeconds;

    //enabled or disabled
    //是否启用
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * 心跳检测频率, 默认30秒
     */
    @Column(name = "frequency")
    @Enumerated(value = EnumType.STRING)
    private HeartBeatFrequency frequency = HeartBeatFrequency.THIRTY;

    /**
     * 若出现测试正常或不正常时提醒的邮件地址
     * 若有多个请用英文分号(;)分隔
     */
    @Column(name = "email")
    private String email;


    /**
     * Schedule中的任务名称,
     * 当启用该监听任务时, 将会有唯一对应的jobName
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    private String remark;

    /**
     * The instance creator (owner)
     */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    /**
     * Default
     */
    public ApplicationInstance() {
    }


    public ApplicationInstanceURL instanceURL() {
        return instanceURL;
    }

    public ApplicationInstance addMonitorURLParameter(InstanceMonitorURLParameter urlParameter) {
        instanceURL.urlParameters().add(urlParameter);
        return this;
    }

    public User creator() {
        return creator;
    }

    public ApplicationInstance creator(User creator) {
        this.creator = creator;
        return this;
    }

    public MonitorUrlRequestMethod requestMethod() {
        return instanceURL.requestMethod();
    }

    public ApplicationInstance requestMethod(MonitorUrlRequestMethod requestMethod) {
        instanceURL.requestMethod(requestMethod);
        return this;
    }

    public String instanceName() {
        return instanceName;
    }

    public ApplicationInstance instanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public String monitorUrl() {
        return instanceURL.monitorUrl();
    }

    public ApplicationInstance monitorUrl(String monitorUrl) {
        instanceURL.monitorUrl(monitorUrl);
        return this;
    }

    public int maxConnectionSeconds() {
        return maxConnectionSeconds;
    }

    public ApplicationInstance maxConnectionSeconds(int maxConnectionSeconds) {
        this.maxConnectionSeconds = maxConnectionSeconds;
        return this;
    }

    public boolean enabled() {
        return enabled;
    }

    public ApplicationInstance enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public HeartBeatFrequency frequency() {
        return frequency;
    }

    public ApplicationInstance frequency(HeartBeatFrequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public String email() {
        return email;
    }

    public String[] emailAsArray() {
        return email.indexOf(";") > 0 ? email.split(";") : new String[]{email};
    }

    public ApplicationInstance email(String email) {
        this.email = email;
        return this;
    }

    public String jobName() {
        return jobName;
    }

    public ApplicationInstance jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String remark() {
        return remark;
    }

    public ApplicationInstance remark(String remark) {
        this.remark = remark;
        return this;
    }
}