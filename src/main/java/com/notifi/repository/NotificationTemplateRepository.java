package com.notifi.repository;

import com.notifi.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, UUID> {
    Optional<NotificationTemplate> findByProjectIdAndName(UUID projectId, String name);
    List<NotificationTemplate> findByProjectId(UUID projectId);
}
