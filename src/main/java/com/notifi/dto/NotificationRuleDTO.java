package com.notifi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationRuleDTO(
    UUID id,
    String eventType,
    UUID templateId,
    UUID projectId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
