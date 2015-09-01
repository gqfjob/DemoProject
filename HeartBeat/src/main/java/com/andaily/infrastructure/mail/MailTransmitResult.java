package com.andaily.infrastructure.mail;

/**
 * 邮件发送的结果
 *
 * @author Shengzhao Li
 */
public class MailTransmitResult {

    private boolean success;
    private Exception exception;

    private long costTime;

    public MailTransmitResult() {
    }

    public boolean success() {
        return success;
    }

    public MailTransmitResult success(boolean success) {
        this.success = success;
        return this;
    }

    public Exception exception() {
        return exception;
    }

    public MailTransmitResult exception(Exception exception) {
        this.exception = exception;
        return this;
    }

    public long costTime() {
        return costTime;
    }

    public MailTransmitResult costTime(long costTime) {
        this.costTime = costTime;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{success=").append(success);
        sb.append(", exception=").append(exception);
        sb.append(", costTime=").append(costTime);
        sb.append('}');
        return sb.toString();
    }
}