package com.andaily.domain.application;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Component of ApplicationInstance
 *
 * @author Shengzhao Li
 */
@Embeddable
public class ApplicationInstanceURL implements Serializable {


    //Monitoring url: http://axxx.com/
    //监测地址URL
    @Column(name = "monitor_url")
    private String monitorUrl;


    /**
     * All available contentTypes: {@link com.andaily.infrastructure.HttpClientHandler#CONTENT_TYPES}
     * Default is null
     */
    @Column(name = "content_type")
    private String contentType;


    /**
     * {@link #monitorUrl}  send request method,
     * default: GET
     */
    @Column(name = "request_method")
    @Enumerated(value = EnumType.STRING)
    private MonitorUrlRequestMethod requestMethod = MonitorUrlRequestMethod.GET;

    //Request parameters(optional)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "instance_id")
    private List<InstanceMonitorURLParameter> urlParameters = new ArrayList<>();


    public ApplicationInstanceURL() {
    }

    public String contentType() {
        return contentType;
    }

    public ApplicationInstanceURL contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public List<InstanceMonitorURLParameter> urlParameters() {
        return urlParameters;
    }

    public MonitorUrlRequestMethod requestMethod() {
        return requestMethod;
    }

    public ApplicationInstanceURL requestMethod(MonitorUrlRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }


    public String monitorUrl() {
        return monitorUrl;
    }

    public ApplicationInstanceURL monitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
        return this;
    }


}