package com.drsimple.jwtsecurity.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppInfo {
    private String appName;
    private String version;
    private int maxUser;
    private boolean isEnableXFeature;
}
