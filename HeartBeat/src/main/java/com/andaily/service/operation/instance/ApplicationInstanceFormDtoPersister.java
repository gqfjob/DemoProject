package com.andaily.service.operation.instance;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.application.ApplicationInstanceRepository;
import com.andaily.domain.application.InstanceMonitorURLParameter;
import com.andaily.domain.dto.application.ApplicationInstanceFormDto;
import com.andaily.domain.dto.application.InstanceMonitorURLParameterDto;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.shared.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 15-1-4
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceFormDtoPersister {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInstanceFormDtoPersister.class);

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private ApplicationInstanceFormDto formDto;

    public ApplicationInstanceFormDtoPersister(ApplicationInstanceFormDto formDto) {
        this.formDto = formDto;
    }

    public void persist() {
        if (formDto.isNewly()) {
            createInstance();
        } else {
            updateInstance();
        }
    }

    private void updateInstance() {
        ApplicationInstance instance = instanceRepository.findByGuid(formDto.getGuid(), ApplicationInstance.class);
        if (instance.enabled()) {
            throw new IllegalStateException("Only Disabled ApplicationInstance support edit");
        }
        formDto.updateDomain(instance);
        setRequestParams(instance);
        LOGGER.debug("<{}> Update ApplicationInstance [{}]", SecurityUtils.currentUsername(), instance);
    }

    private void createInstance() {
        ApplicationInstance instance = formDto.updateDomain(new ApplicationInstance());
        instance.creator(SecurityUtils.currentUser());

        setRequestParams(instance);

        instanceRepository.saveOrUpdate(instance);
        LOGGER.debug("<{}> Create ApplicationInstance [{}]", SecurityUtils.currentUsername(), instance);
    }

    private void setRequestParams(ApplicationInstance instance) {
        final List<InstanceMonitorURLParameter> urlParameters = instance.instanceURL().urlParameters();
        if (!instance.isNewly()) {
            //remove old
            instanceRepository.deleteAll(urlParameters);
            urlParameters.clear();
        }

        final List<InstanceMonitorURLParameterDto> urlParameterDtos = formDto.getUrlParameters();
        for (InstanceMonitorURLParameterDto urlParameterDto : urlParameterDtos) {
            urlParameters.add(urlParameterDto.newDomain());
        }

    }
}
