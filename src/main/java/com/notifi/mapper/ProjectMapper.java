package com.notifi.mapper;

import com.notifi.dto.ProjectDTO;
import com.notifi.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    
    @Mapping(source = "owner.id", target = "ownerId")
    ProjectDTO toDTO(Project project);
}
