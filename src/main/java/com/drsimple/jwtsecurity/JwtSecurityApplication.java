package com.drsimple.jwtsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.drsimple.jwtsecurity")
@EnableAsync
@EnableScheduling
public class JwtSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityApplication.class, args);
    }

}
