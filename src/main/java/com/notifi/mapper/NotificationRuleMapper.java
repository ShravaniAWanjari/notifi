package com.notifi.mapper;

import com.notifi.dto.NotificationRuleDTO;
import com.notifi.entity.NotificationRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationRuleMapper {
    
    @Mapping(source = "template.id", target = "templateId")
    @Mapping(source = "project.id", target = "projectId")
    NotificationRuleDTO toDTO(NotificationRule rule);
}
