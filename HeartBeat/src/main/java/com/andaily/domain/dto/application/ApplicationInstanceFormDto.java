package com.andaily.domain.dto.application;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceURL;
import com.andaily.domain.application.HeartBeatFrequency;
import com.andaily.domain.application.InstanceMonitorURLParameter;
import com.andaily.infrastructure.HttpClientHandler;
import org.apache.http.entity.ContentType;

import java.util.ArrayList;
import java.util.List;

/**
 * 15-1-4
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceFormDto extends ApplicationInstanceDto {


    private List<InstanceMonitorURLParameterDto> urlParameters = new ArrayList<>();


    public ApplicationInstanceFormDto() {
        super();
        //set default max connection time
        this.maxConnectionSeconds = this.frequency.getSeconds();
    }

    public ApplicationInstanceFormDto(ApplicationInstance instance) {
        super(instance);

        initialURL(instance);
    }

    private void initialURL(ApplicationInstance instance) {
        final ApplicationInstanceURL instanceURL = instance.instanceURL();
        for (InstanceMonitorURLParameter urlParameter : instanceURL.urlParameters()) {
            this.urlParameters.add(new InstanceMonitorURLParameterDto(urlParameter));
        }
    }

    public List<InstanceMonitorURLParameterDto> getUrlParameters() {
        return urlParameters;
    }

    public int getUrlParametersSize() {
        return urlParameters.size();
    }

    public void setUrlParameters(List<InstanceMonitorURLParameterDto> urlParameters) {
        this.urlParameters = urlParameters;
    }


    public HeartBeatFrequency[] getFrequencies() {
        return HeartBeatFrequency.values();
    }


    public List<ContentType> getContentTypes() {
        return HttpClientHandler.CONTENT_TYPES;
    }


    public ApplicationInstance updateDomain(ApplicationInstance instance) {
        instance.instanceURL().contentType(contentType);
        return instance.instanceName(instanceName)
                .monitorUrl(monitorUrl)
                .requestMethod(requestMethod)
                .maxConnectionSeconds(maxConnectionSeconds)
                .email(email)
                .frequency(frequency)
                .remark(remark);
    }
}
