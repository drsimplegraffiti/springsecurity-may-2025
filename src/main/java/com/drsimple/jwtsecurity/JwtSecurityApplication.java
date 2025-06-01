package com.drsimple.jwtsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.drsimple.jwtsecurity")
@EnableAsync
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JwtSecurityApplication {

    private static final Logger logger = LoggerFactory.getLogger(JwtSecurityApplication.class);

    public static void main(String[] args) {
        logger.info("Starting JwtSecurityApplication...⚡⚡⚡⚡⚡⚡⚡⚡⚡");
        SpringApplication.run(JwtSecurityApplication.class, args);
    }
    

}
