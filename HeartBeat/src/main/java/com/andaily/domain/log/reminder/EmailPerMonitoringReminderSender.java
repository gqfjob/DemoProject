package com.andaily.domain.log.reminder;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.log.MonitoringReminderLog;
import com.andaily.domain.shared.Application;
import com.andaily.infrastructure.DateUtils;
import com.andaily.infrastructure.STRender;
import com.andaily.infrastructure.mail.MailTransmitter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
public class EmailPerMonitoringReminderSender implements PerMonitoringReminderSender {


    private static final Logger LOGGER = LoggerFactory.getLogger(EmailPerMonitoringReminderSender.class);


    private static final String CHANGE_NORMAL_EMAIL_SUBJECT = "Connect [%s] restore normally  -- HeartBeat";
    private static final String CHANGE_NORMAL_EMAIL_CONTENT_TEMPLATE = "template/instance_change_normal_content.html";

    private static final String CHANGE_NOT_NORMAL_EMAIL_SUBJECT = "Unable to connect [%s]  -- HeartBeat";
    private static final String CHANGE_NOT_NORMAL_EMAIL_CONTENT_TEMPLATE = "template/instance_change_not_normal_content.html";

    private static final String INSTANCE_MONITORING_URL = "monitoring/%s.hb";

    @Override
    public boolean support(FrequencyMonitorLog monitorLog) {
        return StringUtils.isNotEmpty(monitorLog.instance().email());
    }

    @Override
    public void send(MonitoringReminderLog reminderLog, FrequencyMonitorLog monitorLog) {
        reminderLog.receiveEmail(monitorLog.instance().email());
        final boolean normal = monitorLog.normal();
        reminderLog.changeNormal(normal);

        String emailContent;
        if (normal) {
            emailContent = sendChangeNormalMail(monitorLog);
        } else {
            emailContent = sendChangeUnNormalMail(monitorLog);
        }

        reminderLog.emailContent(emailContent);
    }

    /*
   * Subject: Unable to connect [instance_name]  -- HeartBeat
   *
   * */
    private String sendChangeUnNormalMail(FrequencyMonitorLog monitorLog) {
        final ApplicationInstance instance = monitorLog.instance();

        String subject = String.format(CHANGE_NOT_NORMAL_EMAIL_SUBJECT, instance.instanceName());
        final String[] to = instance.emailAsArray();

        Map<String, Object> model = emailContentModel(monitorLog);
        STRender stRender = new STRender(CHANGE_NOT_NORMAL_EMAIL_CONTENT_TEMPLATE, model);
        final String content = stRender.render();

        sendEmail(to, subject, content);
        LOGGER.debug("Sent Change-Not-Normal Email of Instance[guid={},name={}], FrequencyMonitorLog is [{}]", instance.guid(), instance.instanceName(), monitorLog);

        return content;
    }

    /*
    * Subject: Connect [instance_name] restore normally  -- HeartBeat
    *
    * */
    private String sendChangeNormalMail(FrequencyMonitorLog monitorLog) {
        final ApplicationInstance instance = monitorLog.instance();

        String subject = String.format(CHANGE_NORMAL_EMAIL_SUBJECT, instance.instanceName());
        final String[] to = instance.emailAsArray();

        Map<String, Object> model = emailContentModel(monitorLog);
        STRender stRender = new STRender(CHANGE_NORMAL_EMAIL_CONTENT_TEMPLATE, model);
        final String content = stRender.render();

        sendEmail(to, subject, content);
        LOGGER.debug("Sent Change-Normal Email of Instance[guid={},name={}], FrequencyMonitorLog is [{}]", instance.guid(), instance.instanceName(), monitorLog);

        return content;
    }

    private Map<String, Object> emailContentModel(FrequencyMonitorLog monitorLog) {
        final ApplicationInstance instance = monitorLog.instance();

        Map<String, Object> model = new HashMap<>();
        model.put("instanceName", instance.instanceName());
        model.put("url", instance.monitorUrl());
        model.put("costTime", monitorLog.costTime());
        model.put("time", DateUtils.toDateText(monitorLog.createTime(), DateUtils.DEFAULT_DATE_TIME_FORMAT));
        model.put("instanceUrl", instanceUrl(instance.guid()));
        model.put("host", Application.host());
        model.put("remark", monitorLog.remark());
        return model;
    }

    private String instanceUrl(String instanceGuid) {
        String url = String.format(INSTANCE_MONITORING_URL, instanceGuid);
        return Application.host() + url;
    }


    private void sendEmail(String to[], String subject, String content) {
        MailTransmitter transmitter = new MailTransmitter(subject, content, to);
        transmitter.transmit();
        LOGGER.debug("Send MonitoringReminder-Email to {}", Arrays.toString(to));
    }

}