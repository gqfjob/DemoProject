package com.andaily.domain.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author Shengzhao Li
 */
@SuppressWarnings("serial")
public abstract class AbstractDto implements Serializable {

    protected String guid;

    protected AbstractDto() {
    }

    protected AbstractDto(String guid) {
        this.guid = guid;
    }

    public boolean isNewly() {
        return StringUtils.isEmpty(guid);
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}