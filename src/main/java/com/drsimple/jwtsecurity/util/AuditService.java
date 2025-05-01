package com.drsimple.jwtsecurity.util;

import com.drsimple.jwtsecurity.core.AuditLogRepository;
import com.drsimple.jwtsecurity.user.AuditLog;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(Long userId, String action, String resource) {
        auditLogRepository.save(AuditLog.builder()
                .userId(userId)
                .action(action)
                .resource(resource)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
