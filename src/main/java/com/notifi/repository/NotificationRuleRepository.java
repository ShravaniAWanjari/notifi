package com.notifi.repository;

import com.notifi.entity.NotificationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRuleRepository extends JpaRepository<NotificationRule, UUID> {
    Optional<NotificationRule> findByProjectIdAndEventType(UUID projectId, String eventType);
    List<NotificationRule> findByProjectId(UUID projectId);
}
