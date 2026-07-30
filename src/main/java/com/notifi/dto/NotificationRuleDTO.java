package com.notifi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationRuleDTO(
    UUID id,

    @NotBlank(message = "Event type is required")
    String eventType,

    @NotNull(message = "Template ID is required")
    UUID templateId,

    @NotNull(message = "Project ID is required")
    UUID projectId,

    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
