package com.andaily.domain.log;

import com.andaily.domain.AbstractDomain;
import com.andaily.domain.application.ApplicationInstance;

import javax.persistence.*;
import java.util.Date;

/**
 * 14-11-23
 * 实例每一次监控的 日志
 * 这是由定时任务每一次执行后的结果
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "frequency_monitor_log")
public class FrequencyMonitorLog extends AbstractDomain {

    private static final long serialVersionUID = 4517860393571033878L;
    //所属实例
    @ManyToOne
    @JoinColumn(name = "instance_id")
    private ApplicationInstance instance;
    //是否正常
    @Column(name = "normal")
    private boolean normal;

    /**
     * 消耗时间 ms
     */
    @Column(name = "cost_time")
    private long costTime;

    /**
     * 响应返回的数据长度: byte
     */
    @Column(name = "response_size")
    private long responseSize;


    //备注信息
    @Column(name = "remark")
    private String remark;

    //default
    public FrequencyMonitorLog() {
    }

    public ApplicationInstance instance() {
        return instance;
    }

    public FrequencyMonitorLog instance(ApplicationInstance instance) {
        this.instance = instance;
        return this;
    }

    public boolean normal() {
        return normal;
    }

    public FrequencyMonitorLog normal(boolean normal) {
        this.normal = normal;
        return this;
    }

    public long costTime() {
        return costTime;
    }

    public FrequencyMonitorLog costTime(long costTime) {
        this.costTime = costTime;
        return this;
    }

    public String remark() {
        return remark;
    }

    public FrequencyMonitorLog remark(String remark) {
        this.remark = remark;
        return this;
    }

    public FrequencyMonitorLog createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public long responseSize() {
        return responseSize;
    }

    public FrequencyMonitorLog responseSize(long responseSize) {
        this.responseSize = responseSize;
        return this;
    }
}
