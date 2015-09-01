package com.andaily.domain.dto.application;

import com.andaily.domain.application.InstanceMonitorURLParameter;
import com.andaily.domain.dto.AbstractDto;

import javax.persistence.Column;

/**
 * 15-4-25
 *
 * @author Shengzhao Li
 */
public class InstanceMonitorURLParameterDto extends AbstractDto {


    private String key;

    private String value;

    private boolean randomValue = false;


    public InstanceMonitorURLParameterDto() {
    }

    public InstanceMonitorURLParameterDto(InstanceMonitorURLParameter parameter) {
        super(parameter.guid());
        this.key = parameter.key();

        this.value = parameter.value();
        this.randomValue = parameter.randomValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRandomValue() {
        return randomValue;
    }

    public void setRandomValue(boolean randomValue) {
        this.randomValue = randomValue;
    }

    public InstanceMonitorURLParameter newDomain() {
        final InstanceMonitorURLParameter urlParameter = new InstanceMonitorURLParameter()
                .key(key).randomValue(randomValue);
        if (!randomValue) {
            urlParameter.value(value);
        }
        return urlParameter;
    }
}
