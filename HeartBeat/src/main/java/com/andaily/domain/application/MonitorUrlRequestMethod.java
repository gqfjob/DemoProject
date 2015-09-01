package com.andaily.domain.application;

/**
 * 15-3-27
 *
 * @author Shengzhao Li
 */

public enum MonitorUrlRequestMethod {

    GET("Get"),
    POST("Post");

    private String label;

    private MonitorUrlRequestMethod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return name();
    }

    public boolean isGet() {
        return GET.equals(this);
    }

    public boolean isPost() {
        return POST.equals(this);
    }
}
