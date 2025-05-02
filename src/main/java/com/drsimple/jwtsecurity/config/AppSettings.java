package com.drsimple.jwtsecurity.config;


public class AppSettings {

    private String appName;
    private String version;
    private int maxUsers;
    private boolean enableFeatureX;

    // Getters and setters
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public boolean isEnableFeatureX() {
        return enableFeatureX;
    }

    public void setEnableFeatureX(boolean enableFeatureX) {
        this.enableFeatureX = enableFeatureX;
    }
}