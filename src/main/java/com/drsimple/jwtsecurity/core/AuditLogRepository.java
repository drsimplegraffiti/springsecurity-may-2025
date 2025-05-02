package com.drsimple.jwtsecurity.core;

import com.drsimple.jwtsecurity.user.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    void deleteByTimestampBefore(LocalDateTime timestamp);
}
