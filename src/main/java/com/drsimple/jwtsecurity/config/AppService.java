package com.drsimple.jwtsecurity.config;

import org.springframework.stereotype.Service;

@Service
public class AppService {

    private final AppSettings appSettings;

    public AppService(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public AppInfo printAppInfo() {
        System.out.println("App Name: " + appSettings.getAppName());
        System.out.println("Version: " + appSettings.getVersion());
        System.out.println("Max Users: " + appSettings.getMaxUsers());
        System.out.println("Feature X Enabled: " + appSettings.isEnableFeatureX());

        return AppInfo.builder()
                .appName(appSettings.getAppName())
                .version(appSettings.getVersion())
                .maxUser(appSettings.getMaxUsers())
                .isEnableXFeature(appSettings.isEnableFeatureX())
                .build();
    }
}