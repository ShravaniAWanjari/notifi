package com.notifi.mapper;

import com.notifi.dto.NotificationTemplateDTO;
import com.notifi.entity.NotificationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationTemplateMapper {
    
    @Mapping(source = "project.id", target = "projectId")
    NotificationTemplateDTO toDTO(NotificationTemplate template);
}
