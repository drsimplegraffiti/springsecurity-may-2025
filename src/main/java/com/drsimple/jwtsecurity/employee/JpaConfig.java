package com.drsimple.jwtsecurity.employee;

import com.drsimple.jwtsecurity.util.CurrentUserUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

    private final CurrentUserUtil currentUserUtil;

    public JpaConfig(CurrentUserUtil currentUserUtil) {
        this.currentUserUtil = currentUserUtil;
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new ApplicationAuditAware(currentUserUtil);
    }
}
