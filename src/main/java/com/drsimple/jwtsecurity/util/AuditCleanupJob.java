package com.drsimple.jwtsecurity.util;

import com.drsimple.jwtsecurity.core.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditCleanupJob {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Scheduled(cron = "0 0 2 * * ?") // Every day at 2 AM

    public void cleanOldLogs() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        auditLogRepository.deleteByTimestampBefore(thirtyDaysAgo);
    }
}