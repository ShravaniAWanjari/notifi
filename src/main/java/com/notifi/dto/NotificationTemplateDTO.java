package com.notifi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationTemplateDTO(
    UUID id,
    String name,
    String channel,
    String content,
    UUID projectId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
