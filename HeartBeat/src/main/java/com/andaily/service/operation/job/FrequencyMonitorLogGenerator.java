package com.andaily.service.operation.job;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.InstanceMonitorURLParameter;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.infrastructure.HttpClientHandler;
import com.andaily.infrastructure.HttpClientPostHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 15-3-27
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrequencyMonitorLogGenerator.class);
    private ApplicationInstance instance;

    public FrequencyMonitorLogGenerator(ApplicationInstance instance) {
        this.instance = instance;
    }

    public FrequencyMonitorLog generate() {
        HttpClientHandler httpClientHandler = createHttpClientHandler();
        LOGGER.debug("Send Request to URL: {} use HttpClientHandler: {}", monitorUrl(), httpClientHandler);

        final FrequencyMonitorLog monitorLog = httpClientHandler.handleAndGenerateFrequencyMonitorLog();
        monitorLog.instance(instance);
        return monitorLog;
    }

    private HttpClientHandler createHttpClientHandler() {
        HttpClientHandler clientHandler = instance.requestMethod().isPost() ?
                new HttpClientPostHandler(monitorUrl())
                : new HttpClientHandler(monitorUrl());

        final List<InstanceMonitorURLParameter> urlParameters = instance.instanceURL().urlParameters();
        for (InstanceMonitorURLParameter param : urlParameters) {
            clientHandler.addRequestParam(param.key(), param.realValue());
        }

        return clientHandler.maxConnectionSeconds(maxConnectionSeconds())
                .contentType(instance.instanceURL().contentType());
    }

    private int maxConnectionSeconds() {
        return instance.maxConnectionSeconds();
    }

    private String monitorUrl() {
        return instance.monitorUrl();
    }
}
