package com.notifi.dto;

import com.notifi.entity.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationTemplateDTO(
    UUID id,
    
    @NotBlank(message = "Name is required")
    String name,
    
    @NotNull(message = "Channel is required")
    NotificationChannel channel,
    
    @NotBlank(message = "Content is required")
    @Size(max = 65535, message = "Content cannot exceed 64KB")
    String content,
    
    @NotNull(message = "Project ID is required")
    UUID projectId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
