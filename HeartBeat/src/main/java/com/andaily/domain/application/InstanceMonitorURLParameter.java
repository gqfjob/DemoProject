package com.andaily.domain.application;

import com.andaily.domain.AbstractDomain;
import com.andaily.domain.shared.GuidGenerator;

import javax.persistence.*;

/**
 * The monitor url request parameter (optional)
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "instance_monitor_url_parameter")
public class InstanceMonitorURLParameter extends AbstractDomain {

    @Column(name = "key_")
    private String key;

    @Column(name = "value_")
    private String value;

    /**
     * If the value = true,  {@link #value} is null
     */
    @Column(name = "random_value", columnDefinition = "tinyint(1)")
    private boolean randomValue = false;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private ApplicationInstance instance;


    public InstanceMonitorURLParameter() {
    }

    public String key() {
        return key;
    }

    public InstanceMonitorURLParameter key(String key) {
        this.key = key;
        return this;
    }

    public String value() {
        return value;
    }

    public String realValue() {
        if (randomValue) {
            return GuidGenerator.generate();
        }
        return value;
    }

    public InstanceMonitorURLParameter value(String value) {
        this.value = value;
        return this;
    }

    public boolean randomValue() {
        return randomValue;
    }

    public InstanceMonitorURLParameter randomValue(boolean randomValue) {
        this.randomValue = randomValue;
        return this;
    }

    public ApplicationInstance instance() {
        return instance;
    }

    public InstanceMonitorURLParameter instance(ApplicationInstance instance) {
        this.instance = instance;
        return this;
    }
}