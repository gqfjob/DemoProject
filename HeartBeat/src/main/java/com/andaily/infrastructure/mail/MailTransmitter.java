package com.andaily.infrastructure.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送器
 *
 * @author Shengzhao Li
 */
public class MailTransmitter extends Thread implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(MailTransmitter.class);

    protected static JavaMailSender defaultMailSender;

    protected static String defaultFromAddress;
    protected static boolean developEnvironment;
    protected static String developEmailAddress;

    protected static boolean sendMailUseThread;

    //mail fields
    protected String from;
    protected String to[];
    protected String subject;
    protected String content;
    protected Date sendDate;

    protected String cc[];

    protected Map<String, Resource> attachments = new HashMap<>();
    protected Map<String, Resource> inlineResources = new HashMap<>();

    protected transient MailTransmitResult sendResult;

    /**
     * For spring container use it
     */
    public MailTransmitter() {
    }

    public MailTransmitter(String subject, String content, String... to) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        //default value
        this.from = defaultFromAddress;
        this.sendDate = new Date();
    }

    public MailTransmitter from(String from) {
        this.from = from;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends MailTransmitter> T cc(String... cc) {
        this.cc = cc;
        return (T) this;
    }

    public MailTransmitter sendDate(Date sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public MailTransmitter addAttachment(String attachName, Resource resource) {
        this.attachments.put(attachName, resource);
        return this;
    }

    public MailTransmitter addInlineResource(String inlineName, Resource inlineResource) {
        this.inlineResources.put(inlineName, inlineResource);
        return this;
    }

    //For subclass override it
    protected JavaMailSender retrieveMailSender() {
        return defaultMailSender;
    }

    // transmit.
    //Note: If use new thread send mail, will get result is NULL.
    public MailTransmitResult transmit() {
        if (sendMailUseThread) {
            this.start();
        } else {
            this.run();
        }
        return sendResult;
    }

    public void run() {
        try {
            long start = System.currentTimeMillis();

            final JavaMailSender mailSender = retrieveMailSender();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            setCC(helper);
            setTo(helper);

            helper.setSentDate(sendDate);
            helper.setSubject(subject);
            helper.setText(content, true);

            //attachments
            addAttachments(helper);
            //inline resource.
            addInlineResources(helper);
            mailSender.send(message);
            LOG.debug(">>>>> Send email to [{}] finish.", Arrays.toString(to));
            sendResult = new MailTransmitResult().success(true).costTime(System.currentTimeMillis() - start);
        } catch (Exception e) {
            LOG.error("Send Email error", e);
            sendResult = new MailTransmitResult().success(false).exception(e);
        }
    }

    private void setTo(MimeMessageHelper helper) throws MessagingException {
        if (developEnvironment) {
            LOG.warn("NOTE: Current is Test Environment, The email will be send to developer [{}]", developEmailAddress);
            InternetAddress[] addresses = InternetAddress.parse(developEmailAddress);
            helper.setTo(addresses);
        } else {
            helper.setTo(to);
        }
    }

    //ignore development environment
    private void setCC(MimeMessageHelper helper) throws MessagingException {
        if (!developEnvironment && cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
    }

    private void addAttachments(MimeMessageHelper helper) throws MessagingException {
        for (String name : attachments.keySet()) {
            helper.addAttachment(name, attachments.get(name));
        }
    }

    private void addInlineResources(MimeMessageHelper helper) throws MessagingException {
        for (String name : inlineResources.keySet()) {
            helper.addInline(name, inlineResources.get(name));
        }
    }

    // ----------------------- set default values  -----------------------

    public void setDefaultFromAddress(String defaultFromAddress) {
        MailTransmitter.defaultFromAddress = defaultFromAddress;
    }

    public void setDefaultMailSender(JavaMailSender defaultMailSender) {
        MailTransmitter.defaultMailSender = defaultMailSender;
    }

    public void setDevelopEnvironment(boolean developEnvironment) {
        MailTransmitter.developEnvironment = developEnvironment;
    }

    public void setDevelopEmailAddress(String developEmailAddress) {
        MailTransmitter.developEmailAddress = developEmailAddress;
    }

    public void setSendMailUseThread(boolean sendMailUseThread) {
        MailTransmitter.sendMailUseThread = sendMailUseThread;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(defaultFromAddress, "defaultFromAddress is null");
        Assert.notNull(defaultMailSender, "defaultMailSender is null");
    }
}