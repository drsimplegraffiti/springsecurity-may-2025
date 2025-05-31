package com.drsimple.jwtsecurity.employee;


import com.drsimple.jwtsecurity.util.CurrentUserUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class ApplicationAuditAware implements AuditorAware<Long> {

    private final CurrentUserUtil currentUserUtil;

    public ApplicationAuditAware(CurrentUserUtil currentUserUtil) {
        this.currentUserUtil = currentUserUtil;
    }

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            return Optional.of(currentUserUtil.getLoggedInUser().getId());
        } catch (Exception e) {
            return Optional.empty(); // For unauthenticated or system actions
        }
    }
}