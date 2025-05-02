package com.drsimple.jwtsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
        "classpath:transaction-limits-config.xml",
        "classpath:app-settings-config.xml",
        "classpath:applicationContext.xml"
})
public class AppConfig {

}