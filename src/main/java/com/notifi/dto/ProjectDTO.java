package com.notifi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectDTO(
    UUID id,
    String name,
    String description,
    UUID ownerId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
