package com.notifi.mapper;

import com.notifi.dto.UserDTO;
import com.notifi.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
